package com.custom.xtreamplayer;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001:\u0002\u001d\u001eB\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\b\u0010\u0016\u001a\u00020\u0017H\u0002J\u0012\u0010\u0018\u001a\u00020\u00172\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0014J\u0010\u0010\u001b\u001a\u00020\u00172\u0006\u0010\u001c\u001a\u00020\rH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001f"}, d2 = {"Lcom/custom/xtreamplayer/SeriesListActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "categoryId", "", "categoryName", "pass", "recyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "repository", "Lcom/custom/xtreamplayer/DataRepository;", "seriesList", "Ljava/util/ArrayList;", "Lcom/custom/xtreamplayer/ChannelEntity;", "serverUrl", "titleView", "Landroid/widget/TextView;", "user", "dispatchKeyEvent", "", "event", "Landroid/view/KeyEvent;", "loadSeries", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "openSeries", "series", "EmptySeriesAdapter", "SeriesAdapter", "app_debug"})
public final class SeriesListActivity extends androidx.appcompat.app.AppCompatActivity {
    private androidx.recyclerview.widget.RecyclerView recyclerView;
    private android.widget.TextView titleView;
    private com.custom.xtreamplayer.DataRepository repository;
    @org.jetbrains.annotations.NotNull
    private java.lang.String serverUrl = "";
    @org.jetbrains.annotations.NotNull
    private java.lang.String user = "";
    @org.jetbrains.annotations.NotNull
    private java.lang.String pass = "";
    @org.jetbrains.annotations.NotNull
    private java.lang.String categoryId = "";
    @org.jetbrains.annotations.NotNull
    private java.lang.String categoryName = "Series";
    @org.jetbrains.annotations.NotNull
    private final java.util.ArrayList<com.custom.xtreamplayer.ChannelEntity> seriesList = null;
    
    public SeriesListActivity() {
        super();
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    private final void loadSeries() {
    }
    
    private final void openSeries(com.custom.xtreamplayer.ChannelEntity series) {
    }
    
    @java.lang.Override
    public boolean dispatchKeyEvent(@org.jetbrains.annotations.NotNull
    android.view.KeyEvent event) {
        return false;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0004\u0018\u00002\u0010\u0012\f\u0012\n0\u0002R\u00060\u0000R\u00020\u00030\u0001:\u0001\u000fB\u0005\u00a2\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0016J \u0010\u0007\u001a\u00020\b2\u000e\u0010\t\u001a\n0\u0002R\u00060\u0000R\u00020\u00032\u0006\u0010\n\u001a\u00020\u0006H\u0016J \u0010\u000b\u001a\n0\u0002R\u00060\u0000R\u00020\u00032\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0006H\u0016\u00a8\u0006\u0010"}, d2 = {"Lcom/custom/xtreamplayer/SeriesListActivity$EmptySeriesAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/custom/xtreamplayer/SeriesListActivity$EmptySeriesAdapter$ViewHolder;", "Lcom/custom/xtreamplayer/SeriesListActivity;", "(Lcom/custom/xtreamplayer/SeriesListActivity;)V", "getItemCount", "", "onBindViewHolder", "", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "ViewHolder", "app_debug"})
    public final class EmptySeriesAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.custom.xtreamplayer.SeriesListActivity.EmptySeriesAdapter.ViewHolder> {
        
        public EmptySeriesAdapter() {
            super();
        }
        
        @java.lang.Override
        @org.jetbrains.annotations.NotNull
        public com.custom.xtreamplayer.SeriesListActivity.EmptySeriesAdapter.ViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull
        android.view.ViewGroup parent, int viewType) {
            return null;
        }
        
        @java.lang.Override
        public void onBindViewHolder(@org.jetbrains.annotations.NotNull
        com.custom.xtreamplayer.SeriesListActivity.EmptySeriesAdapter.ViewHolder holder, int position) {
        }
        
        @java.lang.Override
        public int getItemCount() {
            return 0;
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f\u00a8\u0006\r"}, d2 = {"Lcom/custom/xtreamplayer/SeriesListActivity$EmptySeriesAdapter$ViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "view", "Landroid/view/View;", "(Lcom/custom/xtreamplayer/SeriesListActivity$EmptySeriesAdapter;Landroid/view/View;)V", "posterView", "Landroid/widget/ImageView;", "getPosterView", "()Landroid/widget/ImageView;", "titleView", "Landroid/widget/TextView;", "getTitleView", "()Landroid/widget/TextView;", "app_debug"})
        public final class ViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
            @org.jetbrains.annotations.NotNull
            private final android.widget.TextView titleView = null;
            @org.jetbrains.annotations.NotNull
            private final android.widget.ImageView posterView = null;
            
            public ViewHolder(@org.jetbrains.annotations.NotNull
            android.view.View view) {
                super(null);
            }
            
            @org.jetbrains.annotations.NotNull
            public final android.widget.TextView getTitleView() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull
            public final android.widget.ImageView getPosterView() {
                return null;
            }
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0004\u0018\u00002\u0010\u0012\f\u0012\n0\u0002R\u00060\u0000R\u00020\u00030\u0001:\u0001\u0012B\u0013\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\u0002\u0010\u0007J\b\u0010\b\u001a\u00020\tH\u0016J \u0010\n\u001a\u00020\u000b2\u000e\u0010\f\u001a\n0\u0002R\u00060\u0000R\u00020\u00032\u0006\u0010\r\u001a\u00020\tH\u0016J \u0010\u000e\u001a\n0\u0002R\u00060\u0000R\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\tH\u0016R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2 = {"Lcom/custom/xtreamplayer/SeriesListActivity$SeriesAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/custom/xtreamplayer/SeriesListActivity$SeriesAdapter$SeriesViewHolder;", "Lcom/custom/xtreamplayer/SeriesListActivity;", "items", "", "Lcom/custom/xtreamplayer/ChannelEntity;", "(Lcom/custom/xtreamplayer/SeriesListActivity;Ljava/util/List;)V", "getItemCount", "", "onBindViewHolder", "", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "SeriesViewHolder", "app_debug"})
    public final class SeriesAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.custom.xtreamplayer.SeriesListActivity.SeriesAdapter.SeriesViewHolder> {
        @org.jetbrains.annotations.NotNull
        private final java.util.List<com.custom.xtreamplayer.ChannelEntity> items = null;
        
        public SeriesAdapter(@org.jetbrains.annotations.NotNull
        java.util.List<com.custom.xtreamplayer.ChannelEntity> items) {
            super();
        }
        
        @java.lang.Override
        @org.jetbrains.annotations.NotNull
        public com.custom.xtreamplayer.SeriesListActivity.SeriesAdapter.SeriesViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull
        android.view.ViewGroup parent, int viewType) {
            return null;
        }
        
        @java.lang.Override
        public void onBindViewHolder(@org.jetbrains.annotations.NotNull
        com.custom.xtreamplayer.SeriesListActivity.SeriesAdapter.SeriesViewHolder holder, int position) {
        }
        
        @java.lang.Override
        public int getItemCount() {
            return 0;
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f\u00a8\u0006\r"}, d2 = {"Lcom/custom/xtreamplayer/SeriesListActivity$SeriesAdapter$SeriesViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "view", "Landroid/view/View;", "(Lcom/custom/xtreamplayer/SeriesListActivity$SeriesAdapter;Landroid/view/View;)V", "posterView", "Landroid/widget/ImageView;", "getPosterView", "()Landroid/widget/ImageView;", "titleView", "Landroid/widget/TextView;", "getTitleView", "()Landroid/widget/TextView;", "app_debug"})
        public final class SeriesViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
            @org.jetbrains.annotations.NotNull
            private final android.widget.TextView titleView = null;
            @org.jetbrains.annotations.NotNull
            private final android.widget.ImageView posterView = null;
            
            public SeriesViewHolder(@org.jetbrains.annotations.NotNull
            android.view.View view) {
                super(null);
            }
            
            @org.jetbrains.annotations.NotNull
            public final android.widget.TextView getTitleView() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull
            public final android.widget.ImageView getPosterView() {
                return null;
            }
        }
    }
}