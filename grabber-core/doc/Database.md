#LogDB

An abstraction on top of RocksDB specific to multi-channel logs. 

##Channel

Helps in segregating messages of different types. 
 
Instead of using separate column family for each type we can use a key prefix and use a single column family. Need to validate but
it seems RocksDB perform better with less column families. MongoRocks and CockroachDB does that. 

##Switcher

If we only need forward the logs and not care about ordering etc. A simple solution could be to implement a switcher. We cab use 
two RocksDB columns instead of one. At any point of time only one column will be use to read and other for write. Once, the column
has been read and emptied we can switch threads. Now, the writer thread will start writing to the previously emptied column and 
reader would start reading from the one previously being written on. 

Instead of deleting often, we can create a `toBeDeleted` column. We can choose our consistency level. If we are fine with forwarding
duplicates then we can add to `toBeDeleted` column after receiving acknowledgment from receiver. If we are fine with loosing some data
(not forwarded) then we can add to `toBeDeleted` column before forwarding. 

We can create a `master` column. This column will be use to maintain LogDB statistics. e.g. Current reader/writer (will help on
restart), prefixes, counts etc. 

Maintaining a `toBeDeleted` column and writing on it heavily may have some impact. We can also provide a low consistency level where
we will keep data to be deleted in memory and switch between columns more frequently. This will mean that data in memory won't 
become a problem. This could be faster but during a restart it may loose some data. This problem can be solved for scheduled restarts
as we can inform LogDB to `prepareForShutDown`. 

Live/Live CD will pose another challenge. As there could still be not forwarded data when new deployment starts. In such cases, application
can have a state `readForShutDown`. So once, informed that it will be shutdown. Application can 

##Indexer

We can implement another implementation, which will use index. We do not need any for now.