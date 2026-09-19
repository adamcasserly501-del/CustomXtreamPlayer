package com.custom.xtreamplayer;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0007\bg\u0018\u00002\u00020\u0001J\u0011\u0010\u0002\u001a\u00020\u0003H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0004J!\u0010\u0005\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0007H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\tJ\u0019\u0010\n\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0007H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000bJ\'\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0007H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\tJ\u001f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00070\r2\u0006\u0010\u0006\u001a\u00020\u0007H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000bJ\u001f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000e0\r2\u0006\u0010\u0006\u001a\u00020\u0007H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000bJ\u001f\u0010\u0011\u001a\u00020\u00032\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u000e0\rH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0013J\u001b\u0010\u0014\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0016\u001a\u00020\u0007H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000bJ\u001f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u000e0\r2\u0006\u0010\u0018\u001a\u00020\u0007H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000bJ!\u0010\u0019\u001a\u00020\u00032\u0006\u0010\u0016\u001a\u00020\u00072\u0006\u0010\u001a\u001a\u00020\u0015H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001b\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u001c"}, d2 = {"Lcom/custom/xtreamplayer/ChannelDao;", "", "clearAll", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "clearCategory", "type", "", "catId", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "clearSection", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getChannelsByCategory", "", "Lcom/custom/xtreamplayer/ChannelEntity;", "getFavoriteIds", "getFavorites", "insertChannels", "channels", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "isFavorite", "", "streamId", "searchChannels", "query", "setFavorite", "isFav", "(Ljava/lang/String;ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@androidx.room.Dao
public abstract interface ChannelDao {
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object insertChannels(@org.jetbrains.annotations.NotNull
    java.util.List<com.custom.xtreamplayer.ChannelEntity> channels, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "\n        SELECT * FROM channels\n        WHERE sectionType = :type AND categoryId = :catId\n        ORDER BY name COLLATE NOCASE ASC\n    ")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getChannelsByCategory(@org.jetbrains.annotations.NotNull
    java.lang.String type, @org.jetbrains.annotations.NotNull
    java.lang.String catId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.util.List<com.custom.xtreamplayer.ChannelEntity>> $completion);
    
    @androidx.room.Query(value = "\n        SELECT * FROM channels\n        WHERE sectionType = :type AND isFavorite = 1\n        ORDER BY name COLLATE NOCASE ASC\n    ")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getFavorites(@org.jetbrains.annotations.NotNull
    java.lang.String type, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.util.List<com.custom.xtreamplayer.ChannelEntity>> $completion);
    
    @androidx.room.Query(value = "SELECT streamId FROM channels WHERE sectionType = :type AND isFavorite = 1")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getFavoriteIds(@org.jetbrains.annotations.NotNull
    java.lang.String type, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.util.List<java.lang.String>> $completion);
    
    @androidx.room.Query(value = "SELECT isFavorite FROM channels WHERE streamId = :streamId LIMIT 1")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object isFavorite(@org.jetbrains.annotations.NotNull
    java.lang.String streamId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion);
    
    @androidx.room.Query(value = "UPDATE channels SET isFavorite = :isFav WHERE streamId = :streamId")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object setFavorite(@org.jetbrains.annotations.NotNull
    java.lang.String streamId, boolean isFav, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM channels WHERE name LIKE \'%\' || :query || \'%\' ORDER BY name COLLATE NOCASE ASC")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object searchChannels(@org.jetbrains.annotations.NotNull
    java.lang.String query, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.util.List<com.custom.xtreamplayer.ChannelEntity>> $completion);
    
    @androidx.room.Query(value = "DELETE FROM channels WHERE sectionType = :type")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object clearSection(@org.jetbrains.annotations.NotNull
    java.lang.String type, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM channels WHERE sectionType = :type AND categoryId = :catId")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object clearCategory(@org.jetbrains.annotations.NotNull
    java.lang.String type, @org.jetbrains.annotations.NotNull
    java.lang.String catId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM channels")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object clearAll(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}