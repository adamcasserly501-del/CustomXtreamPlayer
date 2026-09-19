package com.custom.xtreamplayer;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\u0018\u00002\u00020\u0001:\u0002()B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00052\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\u0010\u0010\u0018\u001a\u00020\u00102\u0006\u0010\u0019\u001a\u00020\u001aH\u0002J\b\u0010\u001b\u001a\u00020\u0010H\u0002J!\u0010\u001c\u001a\u00020\u00052\u0012\u0010\u001d\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00050\u001e\"\u00020\u0005H\u0002\u00a2\u0006\u0002\u0010\u001fJ\u0012\u0010 \u001a\u00020\u00102\b\u0010!\u001a\u0004\u0018\u00010\"H\u0014J\u0010\u0010#\u001a\u00020\u00102\u0006\u0010$\u001a\u00020%H\u0002J\b\u0010&\u001a\u00020\u001aH\u0002J\b\u0010\'\u001a\u00020\u0010H\u0002R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006*"}, d2 = {"Lcom/custom/xtreamplayer/SeriesActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "episodeExts", "Ljava/util/ArrayList;", "", "episodeIds", "episodeNames", "pass", "recyclerViewEpisodes", "Landroidx/recyclerview/widget/RecyclerView;", "seriesId", "seriesName", "serverUrl", "user", "addSeasonEpisodes", "", "seasonNumber", "seasonArray", "Lorg/json/JSONArray;", "dispatchKeyEvent", "", "event", "Landroid/view/KeyEvent;", "displayEpisodes", "jsonObject", "Lorg/json/JSONObject;", "fetchSeriesEpisodes", "firstNonEmpty", "values", "", "([Ljava/lang/String;)Ljava/lang/String;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "openEpisode", "position", "", "requestSeriesInfo", "showNoEpisodes", "EpisodeAdapter", "NoEpisodesAdapter", "app_debug"})
public final class SeriesActivity extends androidx.appcompat.app.AppCompatActivity {
    private androidx.recyclerview.widget.RecyclerView recyclerViewEpisodes;
    @org.jetbrains.annotations.NotNull
    private final java.util.ArrayList<java.lang.String> episodeNames = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.ArrayList<java.lang.String> episodeIds = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.ArrayList<java.lang.String> episodeExts = null;
    @org.jetbrains.annotations.NotNull
    private java.lang.String seriesId = "";
    @org.jetbrains.annotations.NotNull
    private java.lang.String seriesName = "";
    @org.jetbrains.annotations.NotNull
    private java.lang.String serverUrl = "";
    @org.jetbrains.annotations.NotNull
    private java.lang.String user = "";
    @org.jetbrains.annotations.NotNull
    private java.lang.String pass = "";
    
    public SeriesActivity() {
        super();
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    private final void fetchSeriesEpisodes() {
    }
    
    private final org.json.JSONObject requestSeriesInfo() {
        return null;
    }
    
    private final void displayEpisodes(org.json.JSONObject jsonObject) {
    }
    
    private final void addSeasonEpisodes(java.lang.String seasonNumber, org.json.JSONArray seasonArray) {
    }
    
    private final void openEpisode(int position) {
    }
    
    private final void showNoEpisodes() {
    }
    
    @java.lang.Override
    public boolean dispatchKeyEvent(@org.jetbrains.annotations.NotNull
    android.view.KeyEvent event) {
        return false;
    }
    
    private final java.lang.String firstNonEmpty(java.lang.String... values) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0004\u0018\u00002\u0010\u0012\f\u0012\n0\u0002R\u00060\u0000R\u00020\u00030\u0001:\u0001\u0012B\u0013\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\u0002\u0010\u0007J\b\u0010\b\u001a\u00020\tH\u0016J \u0010\n\u001a\u00020\u000b2\u000e\u0010\f\u001a\n0\u0002R\u00060\u0000R\u00020\u00032\u0006\u0010\r\u001a\u00020\tH\u0016J \u0010\u000e\u001a\n0\u0002R\u00060\u0000R\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\tH\u0016R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2 = {"Lcom/custom/xtreamplayer/SeriesActivity$EpisodeAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/custom/xtreamplayer/SeriesActivity$EpisodeAdapter$EpisodeViewHolder;", "Lcom/custom/xtreamplayer/SeriesActivity;", "names", "", "", "(Lcom/custom/xtreamplayer/SeriesActivity;Ljava/util/List;)V", "getItemCount", "", "onBindViewHolder", "", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "EpisodeViewHolder", "app_debug"})
    public final class EpisodeAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.custom.xtreamplayer.SeriesActivity.EpisodeAdapter.EpisodeViewHolder> {
        @org.jetbrains.annotations.NotNull
        private final java.util.List<java.lang.String> names = null;
        
        public EpisodeAdapter(@org.jetbrains.annotations.NotNull
        java.util.List<java.lang.String> names) {
            super();
        }
        
        @java.lang.Override
        @org.jetbrains.annotations.NotNull
        public com.custom.xtreamplayer.SeriesActivity.EpisodeAdapter.EpisodeViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull
        android.view.ViewGroup parent, int viewType) {
            return null;
        }
        
        @java.lang.Override
        public void onBindViewHolder(@org.jetbrains.annotations.NotNull
        com.custom.xtreamplayer.SeriesActivity.EpisodeAdapter.EpisodeViewHolder holder, int position) {
        }
        
        @java.lang.Override
        public int getItemCount() {
            return 0;
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0019\u0010\u0005\u001a\n \u0007*\u0004\u0018\u00010\u00060\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0019\u0010\n\u001a\n \u0007*\u0004\u0018\u00010\u000b0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r\u00a8\u0006\u000e"}, d2 = {"Lcom/custom/xtreamplayer/SeriesActivity$EpisodeAdapter$EpisodeViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "view", "Landroid/view/View;", "(Lcom/custom/xtreamplayer/SeriesActivity$EpisodeAdapter;Landroid/view/View;)V", "posterView", "Landroid/widget/ImageView;", "kotlin.jvm.PlatformType", "getPosterView", "()Landroid/widget/ImageView;", "titleView", "Landroid/widget/TextView;", "getTitleView", "()Landroid/widget/TextView;", "app_debug"})
        public final class EpisodeViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
            private final android.widget.TextView titleView = null;
            private final android.widget.ImageView posterView = null;
            
            public EpisodeViewHolder(@org.jetbrains.annotations.NotNull
            android.view.View view) {
                super(null);
            }
            
            public final android.widget.TextView getTitleView() {
                return null;
            }
            
            public final android.widget.ImageView getPosterView() {
                return null;
            }
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0004\u0018\u00002\u0010\u0012\f\u0012\n0\u0002R\u00060\u0000R\u00020\u00030\u0001:\u0001\u000fB\u0005\u00a2\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0016J \u0010\u0007\u001a\u00020\b2\u000e\u0010\t\u001a\n0\u0002R\u00060\u0000R\u00020\u00032\u0006\u0010\n\u001a\u00020\u0006H\u0016J \u0010\u000b\u001a\n0\u0002R\u00060\u0000R\u00020\u00032\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0006H\u0016\u00a8\u0006\u0010"}, d2 = {"Lcom/custom/xtreamplayer/SeriesActivity$NoEpisodesAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/custom/xtreamplayer/SeriesActivity$NoEpisodesAdapter$ViewHolder;", "Lcom/custom/xtreamplayer/SeriesActivity;", "(Lcom/custom/xtreamplayer/SeriesActivity;)V", "getItemCount", "", "onBindViewHolder", "", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "ViewHolder", "app_debug"})
    public final class NoEpisodesAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.custom.xtreamplayer.SeriesActivity.NoEpisodesAdapter.ViewHolder> {
        
        public NoEpisodesAdapter() {
            super();
        }
        
        @java.lang.Override
        @org.jetbrains.annotations.NotNull
        public com.custom.xtreamplayer.SeriesActivity.NoEpisodesAdapter.ViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull
        android.view.ViewGroup parent, int viewType) {
            return null;
        }
        
        @java.lang.Override
        public void onBindViewHolder(@org.jetbrains.annotations.NotNull
        com.custom.xtreamplayer.SeriesActivity.NoEpisodesAdapter.ViewHolder holder, int position) {
        }
        
        @java.lang.Override
        public int getItemCount() {
            return 0;
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0019\u0010\u0005\u001a\n \u0007*\u0004\u0018\u00010\u00060\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0019\u0010\n\u001a\n \u0007*\u0004\u0018\u00010\u000b0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r\u00a8\u0006\u000e"}, d2 = {"Lcom/custom/xtreamplayer/SeriesActivity$NoEpisodesAdapter$ViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "view", "Landroid/view/View;", "(Lcom/custom/xtreamplayer/SeriesActivity$NoEpisodesAdapter;Landroid/view/View;)V", "posterView", "Landroid/widget/ImageView;", "kotlin.jvm.PlatformType", "getPosterView", "()Landroid/widget/ImageView;", "titleView", "Landroid/widget/TextView;", "getTitleView", "()Landroid/widget/TextView;", "app_debug"})
        public final class ViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
            private final android.widget.TextView titleView = null;
            private final android.widget.ImageView posterView = null;
            
            public ViewHolder(@org.jetbrains.annotations.NotNull
            android.view.View view) {
                super(null);
            }
            
            public final android.widget.TextView getTitleView() {
                return null;
            }
            
            public final android.widget.ImageView getPosterView() {
                return null;
            }
        }
    }
}