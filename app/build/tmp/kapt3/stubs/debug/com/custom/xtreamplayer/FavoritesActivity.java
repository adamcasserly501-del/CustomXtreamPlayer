package com.custom.xtreamplayer;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001:\u0001\u001cB\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\f\u001a\u00020\rH\u0002J\b\u0010\u000e\u001a\u00020\rH\u0002J\u0012\u0010\u000f\u001a\u00020\r2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0014J\u001a\u0010\u0012\u001a\u00020\u000b2\u0006\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0016J\u0010\u0010\u0017\u001a\u00020\r2\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\u0010\u0010\u001a\u001a\u00020\r2\u0006\u0010\u001b\u001a\u00020\u0019H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001d"}, d2 = {"Lcom/custom/xtreamplayer/FavoritesActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "btnTabLiveVod", "Landroid/widget/Button;", "btnTabSeries", "recyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "repository", "Lcom/custom/xtreamplayer/DataRepository;", "showingSeries", "", "loadLiveVodFavorites", "", "loadSeriesFavorites", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onKeyDown", "keyCode", "", "event", "Landroid/view/KeyEvent;", "openLiveOrMovie", "item", "Lcom/custom/xtreamplayer/ChannelEntity;", "openSeries", "series", "FavoritesAdapter", "app_debug"})
public final class FavoritesActivity extends androidx.appcompat.app.AppCompatActivity {
    private androidx.recyclerview.widget.RecyclerView recyclerView;
    private android.widget.Button btnTabLiveVod;
    private android.widget.Button btnTabSeries;
    private com.custom.xtreamplayer.DataRepository repository;
    private boolean showingSeries = false;
    
    public FavoritesActivity() {
        super();
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    private final void loadLiveVodFavorites() {
    }
    
    private final void loadSeriesFavorites() {
    }
    
    private final void openSeries(com.custom.xtreamplayer.ChannelEntity series) {
    }
    
    private final void openLiveOrMovie(com.custom.xtreamplayer.ChannelEntity item) {
    }
    
    @java.lang.Override
    public boolean onKeyDown(int keyCode, @org.jetbrains.annotations.Nullable
    android.view.KeyEvent event) {
        return false;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0004\u0018\u00002\u0010\u0012\f\u0012\n0\u0002R\u00060\u0000R\u00020\u00030\u0001:\u0001\u0014B\u001b\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u00a2\u0006\u0002\u0010\tJ\b\u0010\n\u001a\u00020\u000bH\u0016J \u0010\f\u001a\u00020\r2\u000e\u0010\u000e\u001a\n0\u0002R\u00060\u0000R\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u000bH\u0016J \u0010\u0010\u001a\n0\u0002R\u00060\u0000R\u00020\u00032\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u000bH\u0016R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2 = {"Lcom/custom/xtreamplayer/FavoritesActivity$FavoritesAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/custom/xtreamplayer/FavoritesActivity$FavoritesAdapter$ViewHolder;", "Lcom/custom/xtreamplayer/FavoritesActivity;", "items", "", "Lcom/custom/xtreamplayer/ChannelEntity;", "isSeries", "", "(Lcom/custom/xtreamplayer/FavoritesActivity;Ljava/util/List;Z)V", "getItemCount", "", "onBindViewHolder", "", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "ViewHolder", "app_debug"})
    public final class FavoritesAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.custom.xtreamplayer.FavoritesActivity.FavoritesAdapter.ViewHolder> {
        @org.jetbrains.annotations.NotNull
        private final java.util.List<com.custom.xtreamplayer.ChannelEntity> items = null;
        private final boolean isSeries = false;
        
        public FavoritesAdapter(@org.jetbrains.annotations.NotNull
        java.util.List<com.custom.xtreamplayer.ChannelEntity> items, boolean isSeries) {
            super();
        }
        
        @java.lang.Override
        @org.jetbrains.annotations.NotNull
        public com.custom.xtreamplayer.FavoritesActivity.FavoritesAdapter.ViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull
        android.view.ViewGroup parent, int viewType) {
            return null;
        }
        
        @java.lang.Override
        public void onBindViewHolder(@org.jetbrains.annotations.NotNull
        com.custom.xtreamplayer.FavoritesActivity.FavoritesAdapter.ViewHolder holder, int position) {
        }
        
        @java.lang.Override
        public int getItemCount() {
            return 0;
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0019\u0010\u0005\u001a\n \u0007*\u0004\u0018\u00010\u00060\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0019\u0010\n\u001a\n \u0007*\u0004\u0018\u00010\u000b0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r\u00a8\u0006\u000e"}, d2 = {"Lcom/custom/xtreamplayer/FavoritesActivity$FavoritesAdapter$ViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "view", "Landroid/view/View;", "(Lcom/custom/xtreamplayer/FavoritesActivity$FavoritesAdapter;Landroid/view/View;)V", "posterView", "Landroid/widget/ImageView;", "kotlin.jvm.PlatformType", "getPosterView", "()Landroid/widget/ImageView;", "titleView", "Landroid/widget/TextView;", "getTitleView", "()Landroid/widget/TextView;", "app_debug"})
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