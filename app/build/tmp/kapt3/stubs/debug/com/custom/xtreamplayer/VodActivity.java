package com.custom.xtreamplayer;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\u0016B\u0005\u00a2\u0006\u0002\u0010\u0002J \u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u0004H\u0002J\u0012\u0010\u0010\u001a\u00020\f2\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0014J\u0010\u0010\u0013\u001a\u00020\f2\u0006\u0010\u0014\u001a\u00020\u0015H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0017"}, d2 = {"Lcom/custom/xtreamplayer/VodActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "pass", "", "recyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "repository", "Lcom/custom/xtreamplayer/DataRepository;", "serverUrl", "user", "loadMovies", "", "sectionType", "categoryId", "categoryName", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "openMovie", "movie", "Lcom/custom/xtreamplayer/ChannelEntity;", "VodAdapter", "app_debug"})
public final class VodActivity extends androidx.appcompat.app.AppCompatActivity {
    private androidx.recyclerview.widget.RecyclerView recyclerView;
    private com.custom.xtreamplayer.DataRepository repository;
    @org.jetbrains.annotations.NotNull
    private java.lang.String serverUrl = "";
    @org.jetbrains.annotations.NotNull
    private java.lang.String user = "";
    @org.jetbrains.annotations.NotNull
    private java.lang.String pass = "";
    
    public VodActivity() {
        super();
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    private final void loadMovies(java.lang.String sectionType, java.lang.String categoryId, java.lang.String categoryName) {
    }
    
    private final void openMovie(com.custom.xtreamplayer.ChannelEntity movie) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0004\u0018\u00002\u0010\u0012\f\u0012\n0\u0002R\u00060\u0000R\u00020\u00030\u0001:\u0001\u0012B\u0013\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\u0002\u0010\u0007J\b\u0010\b\u001a\u00020\tH\u0016J \u0010\n\u001a\u00020\u000b2\u000e\u0010\f\u001a\n0\u0002R\u00060\u0000R\u00020\u00032\u0006\u0010\r\u001a\u00020\tH\u0016J \u0010\u000e\u001a\n0\u0002R\u00060\u0000R\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\tH\u0016R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2 = {"Lcom/custom/xtreamplayer/VodActivity$VodAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/custom/xtreamplayer/VodActivity$VodAdapter$MovieViewHolder;", "Lcom/custom/xtreamplayer/VodActivity;", "movies", "", "Lcom/custom/xtreamplayer/ChannelEntity;", "(Lcom/custom/xtreamplayer/VodActivity;Ljava/util/List;)V", "getItemCount", "", "onBindViewHolder", "", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "MovieViewHolder", "app_debug"})
    public final class VodAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.custom.xtreamplayer.VodActivity.VodAdapter.MovieViewHolder> {
        @org.jetbrains.annotations.NotNull
        private final java.util.List<com.custom.xtreamplayer.ChannelEntity> movies = null;
        
        public VodAdapter(@org.jetbrains.annotations.NotNull
        java.util.List<com.custom.xtreamplayer.ChannelEntity> movies) {
            super();
        }
        
        @java.lang.Override
        @org.jetbrains.annotations.NotNull
        public com.custom.xtreamplayer.VodActivity.VodAdapter.MovieViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull
        android.view.ViewGroup parent, int viewType) {
            return null;
        }
        
        @java.lang.Override
        public void onBindViewHolder(@org.jetbrains.annotations.NotNull
        com.custom.xtreamplayer.VodActivity.VodAdapter.MovieViewHolder holder, int position) {
        }
        
        @java.lang.Override
        public int getItemCount() {
            return 0;
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f\u00a8\u0006\r"}, d2 = {"Lcom/custom/xtreamplayer/VodActivity$VodAdapter$MovieViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "view", "Landroid/view/View;", "(Lcom/custom/xtreamplayer/VodActivity$VodAdapter;Landroid/view/View;)V", "poster", "Landroid/widget/ImageView;", "getPoster", "()Landroid/widget/ImageView;", "title", "Landroid/widget/TextView;", "getTitle", "()Landroid/widget/TextView;", "app_debug"})
        public final class MovieViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
            @org.jetbrains.annotations.NotNull
            private final android.widget.TextView title = null;
            @org.jetbrains.annotations.NotNull
            private final android.widget.ImageView poster = null;
            
            public MovieViewHolder(@org.jetbrains.annotations.NotNull
            android.view.View view) {
                super(null);
            }
            
            @org.jetbrains.annotations.NotNull
            public final android.widget.TextView getTitle() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull
            public final android.widget.ImageView getPoster() {
                return null;
            }
        }
    }
}