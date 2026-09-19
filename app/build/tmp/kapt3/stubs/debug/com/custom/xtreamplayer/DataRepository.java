package com.custom.xtreamplayer;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\t\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J!\u0010\u0007\u001a\u00020\b2\u0012\u0010\t\u001a\n\u0012\u0006\b\u0001\u0012\u00020\b0\n\"\u00020\bH\u0002\u00a2\u0006\u0002\u0010\u000bJ\u0018\u0010\f\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\bH\u0002J\'\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u00112\u0006\u0010\u000f\u001a\u00020\b2\u0006\u0010\u0013\u001a\u00020\bH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0014J\u001f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00120\u00112\u0006\u0010\u0016\u001a\u00020\bH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0017J!\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\b2\u0006\u0010\u001b\u001a\u00020\u001cH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001dJA\u0010\u001e\u001a\u00020\u00192\u0006\u0010\u001f\u001a\u00020\b2\u0006\u0010 \u001a\u00020\b2\u0006\u0010!\u001a\u00020\b2\u0006\u0010\"\u001a\u00020\b2\u0006\u0010\u0013\u001a\u00020\b2\u0006\u0010#\u001a\u00020\bH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010$R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006%"}, d2 = {"Lcom/custom/xtreamplayer/DataRepository;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "channelDao", "Lcom/custom/xtreamplayer/ChannelDao;", "firstNonEmpty", "", "values", "", "([Ljava/lang/String;)Ljava/lang/String;", "getArtworkUrl", "obj", "Lorg/json/JSONObject;", "type", "getChannelsFromDb", "", "Lcom/custom/xtreamplayer/ChannelEntity;", "categoryId", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "searchChannels", "query", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setFavorite", "", "streamId", "favorite", "", "(Ljava/lang/String;ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "syncCategoryStreams", "serverUrl", "user", "pass", "sectionType", "categoryName", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class DataRepository {
    @org.jetbrains.annotations.NotNull
    private final com.custom.xtreamplayer.ChannelDao channelDao = null;
    
    public DataRepository(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        super();
    }
    
    /**
     * Download streams/items for one category and save them
     * into the local Room database.
     *
     * LIVE:
     *    get_live_streams
     *
     * VOD:
     *    get_vod_streams
     *
     * SERIES:
     *    get_series
     */
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object syncCategoryStreams(@org.jetbrains.annotations.NotNull
    java.lang.String serverUrl, @org.jetbrains.annotations.NotNull
    java.lang.String user, @org.jetbrains.annotations.NotNull
    java.lang.String pass, @org.jetbrains.annotations.NotNull
    java.lang.String sectionType, @org.jetbrains.annotations.NotNull
    java.lang.String categoryId, @org.jetbrains.annotations.NotNull
    java.lang.String categoryName, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * Extract artwork from an Xtream API object.
     *
     * Different IPTV providers return artwork using
     * different JSON field names.
     */
    private final java.lang.String getArtworkUrl(org.json.JSONObject obj, java.lang.String type) {
        return null;
    }
    
    /**
     * Return cached items from Room.
     *
     * FAVORITES_ID:
     *    returns only favourites.
     *
     * Normal category:
     *    returns items belonging to that category.
     */
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object getChannelsFromDb(@org.jetbrains.annotations.NotNull
    java.lang.String type, @org.jetbrains.annotations.NotNull
    java.lang.String categoryId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.util.List<com.custom.xtreamplayer.ChannelEntity>> $completion) {
        return null;
    }
    
    /**
     * Change favourite state for an item.
     */
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object setFavorite(@org.jetbrains.annotations.NotNull
    java.lang.String streamId, boolean favorite, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * Search cached items by name.
     *
     * SearchActivity uses this method.
     */
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object searchChannels(@org.jetbrains.annotations.NotNull
    java.lang.String query, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.util.List<com.custom.xtreamplayer.ChannelEntity>> $completion) {
        return null;
    }
    
    /**
     * Return the first non-empty string.
     */
    private final java.lang.String firstNonEmpty(java.lang.String... values) {
        return null;
    }
}