package com.custom.xtreamplayer;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0014J\u0018\u0010\u0014\u001a\u00020\u00112\u0006\u0010\u0015\u001a\u00020\u00072\u0006\u0010\u0016\u001a\u00020\u0007H\u0002J\u0010\u0010\u0017\u001a\u00020\u00112\u0006\u0010\u0018\u001a\u00020\u0019H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001a"}, d2 = {"Lcom/custom/xtreamplayer/SearchActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "btnExecuteSearch", "Landroid/widget/Button;", "countries", "", "", "etSearchQuery", "Landroid/widget/EditText;", "recyclerViewSearchResults", "Landroidx/recyclerview/widget/RecyclerView;", "repository", "Lcom/custom/xtreamplayer/DataRepository;", "spinnerCountries", "Landroid/widget/AutoCompleteTextView;", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "performSearch", "country", "query", "playChannel", "channel", "Lcom/custom/xtreamplayer/ChannelEntity;", "app_debug"})
public final class SearchActivity extends androidx.appcompat.app.AppCompatActivity {
    private android.widget.AutoCompleteTextView spinnerCountries;
    private android.widget.EditText etSearchQuery;
    private android.widget.Button btnExecuteSearch;
    private androidx.recyclerview.widget.RecyclerView recyclerViewSearchResults;
    private com.custom.xtreamplayer.DataRepository repository;
    @org.jetbrains.annotations.NotNull
    private final java.util.List<java.lang.String> countries = null;
    
    public SearchActivity() {
        super();
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    private final void performSearch(java.lang.String country, java.lang.String query) {
    }
    
    private final void playChannel(com.custom.xtreamplayer.ChannelEntity channel) {
    }
}