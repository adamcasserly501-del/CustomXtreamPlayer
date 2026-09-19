package com.custom.xtreamplayer;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\u0014B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u000f\u001a\u00020\u0010H\u0002J\u0012\u0010\u0011\u001a\u00020\u00102\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0014R\u0012\u0010\u0003\u001a\u00060\u0004R\u00020\u0000X\u0082.\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2 = {"Lcom/custom/xtreamplayer/CategoriesActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "adapter", "Lcom/custom/xtreamplayer/CategoriesActivity$CategoryAdapter;", "categoryIds", "Ljava/util/ArrayList;", "", "categoryNames", "pass", "recyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "sectionType", "serverUrl", "user", "fetchCategories", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "CategoryAdapter", "app_debug"})
public final class CategoriesActivity extends androidx.appcompat.app.AppCompatActivity {
    private androidx.recyclerview.widget.RecyclerView recyclerView;
    private com.custom.xtreamplayer.CategoriesActivity.CategoryAdapter adapter;
    @org.jetbrains.annotations.NotNull
    private final java.util.ArrayList<java.lang.String> categoryNames = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.ArrayList<java.lang.String> categoryIds = null;
    @org.jetbrains.annotations.NotNull
    private java.lang.String serverUrl = "";
    @org.jetbrains.annotations.NotNull
    private java.lang.String user = "";
    @org.jetbrains.annotations.NotNull
    private java.lang.String pass = "";
    @org.jetbrains.annotations.NotNull
    private java.lang.String sectionType = "LIVE";
    
    public CategoriesActivity() {
        super();
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    private final void fetchCategories() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0004\u0018\u00002\u0010\u0012\f\u0012\n0\u0002R\u00060\u0000R\u00020\u00030\u0001:\u0001\u0013B!\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\u0002\u0010\bJ\b\u0010\t\u001a\u00020\nH\u0016J \u0010\u000b\u001a\u00020\f2\u000e\u0010\r\u001a\n0\u0002R\u00060\u0000R\u00020\u00032\u0006\u0010\u000e\u001a\u00020\nH\u0016J \u0010\u000f\u001a\n0\u0002R\u00060\u0000R\u00020\u00032\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\nH\u0016R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0014"}, d2 = {"Lcom/custom/xtreamplayer/CategoriesActivity$CategoryAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/custom/xtreamplayer/CategoriesActivity$CategoryAdapter$ViewHolder;", "Lcom/custom/xtreamplayer/CategoriesActivity;", "names", "", "", "ids", "(Lcom/custom/xtreamplayer/CategoriesActivity;Ljava/util/List;Ljava/util/List;)V", "getItemCount", "", "onBindViewHolder", "", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "ViewHolder", "app_debug"})
    public final class CategoryAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.custom.xtreamplayer.CategoriesActivity.CategoryAdapter.ViewHolder> {
        @org.jetbrains.annotations.NotNull
        private final java.util.List<java.lang.String> names = null;
        @org.jetbrains.annotations.NotNull
        private final java.util.List<java.lang.String> ids = null;
        
        public CategoryAdapter(@org.jetbrains.annotations.NotNull
        java.util.List<java.lang.String> names, @org.jetbrains.annotations.NotNull
        java.util.List<java.lang.String> ids) {
            super();
        }
        
        @java.lang.Override
        @org.jetbrains.annotations.NotNull
        public com.custom.xtreamplayer.CategoriesActivity.CategoryAdapter.ViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull
        android.view.ViewGroup parent, int viewType) {
            return null;
        }
        
        @java.lang.Override
        public void onBindViewHolder(@org.jetbrains.annotations.NotNull
        com.custom.xtreamplayer.CategoriesActivity.CategoryAdapter.ViewHolder holder, int position) {
        }
        
        @java.lang.Override
        public int getItemCount() {
            return 0;
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u000f"}, d2 = {"Lcom/custom/xtreamplayer/CategoriesActivity$CategoryAdapter$ViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "view", "Landroid/view/View;", "(Lcom/custom/xtreamplayer/CategoriesActivity$CategoryAdapter;Landroid/view/View;)V", "imageView", "Landroid/widget/ImageView;", "getImageView", "()Landroid/widget/ImageView;", "textView", "Landroid/widget/TextView;", "getTextView", "()Landroid/widget/TextView;", "getView", "()Landroid/view/View;", "app_debug"})
        public final class ViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
            @org.jetbrains.annotations.NotNull
            private final android.view.View view = null;
            @org.jetbrains.annotations.NotNull
            private final android.widget.TextView textView = null;
            @org.jetbrains.annotations.NotNull
            private final android.widget.ImageView imageView = null;
            
            public ViewHolder(@org.jetbrains.annotations.NotNull
            android.view.View view) {
                super(null);
            }
            
            @org.jetbrains.annotations.NotNull
            public final android.view.View getView() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull
            public final android.widget.TextView getTextView() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull
            public final android.widget.ImageView getImageView() {
                return null;
            }
        }
    }
}