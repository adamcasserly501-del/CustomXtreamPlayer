package com.custom.xtreamplayer;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0019BI\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\n0\t\u0012\u0016\b\u0002\u0010\u000b\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\n\u0018\u00010\t\u00a2\u0006\u0002\u0010\fJ\b\u0010\u000f\u001a\u00020\u000eH\u0016J\u0006\u0010\u0010\u001a\u00020\u000eJ\u0018\u0010\u0011\u001a\u00020\n2\u0006\u0010\u0012\u001a\u00020\u00022\u0006\u0010\u0013\u001a\u00020\u000eH\u0016J\u0018\u0010\u0014\u001a\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u000eH\u0016J\u000e\u0010\u0018\u001a\u00020\n2\u0006\u0010\u0013\u001a\u00020\u000eR\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001c\u0010\u000b\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\n\u0018\u00010\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001a"}, d2 = {"Lcom/custom/xtreamplayer/ChannelAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/custom/xtreamplayer/ChannelAdapter$ChannelViewHolder;", "channels", "", "Lcom/custom/xtreamplayer/ChannelEntity;", "isLiveTv", "", "onClick", "Lkotlin/Function1;", "", "onLongClick", "(Ljava/util/List;ZLkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)V", "selectedPosition", "", "getItemCount", "getSelectedPosition", "onBindViewHolder", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "setSelectedPosition", "ChannelViewHolder", "app_debug"})
public final class ChannelAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.custom.xtreamplayer.ChannelAdapter.ChannelViewHolder> {
    @org.jetbrains.annotations.NotNull
    private final java.util.List<com.custom.xtreamplayer.ChannelEntity> channels = null;
    private final boolean isLiveTv = false;
    @org.jetbrains.annotations.NotNull
    private final kotlin.jvm.functions.Function1<com.custom.xtreamplayer.ChannelEntity, kotlin.Unit> onClick = null;
    @org.jetbrains.annotations.Nullable
    private final kotlin.jvm.functions.Function1<com.custom.xtreamplayer.ChannelEntity, kotlin.Unit> onLongClick = null;
    private int selectedPosition = -1;
    
    public ChannelAdapter(@org.jetbrains.annotations.NotNull
    java.util.List<com.custom.xtreamplayer.ChannelEntity> channels, boolean isLiveTv, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super com.custom.xtreamplayer.ChannelEntity, kotlin.Unit> onClick, @org.jetbrains.annotations.Nullable
    kotlin.jvm.functions.Function1<? super com.custom.xtreamplayer.ChannelEntity, kotlin.Unit> onLongClick) {
        super();
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public com.custom.xtreamplayer.ChannelAdapter.ChannelViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull
    com.custom.xtreamplayer.ChannelAdapter.ChannelViewHolder holder, int position) {
    }
    
    public final int getSelectedPosition() {
        return 0;
    }
    
    public final void setSelectedPosition(int position) {
    }
    
    @java.lang.Override
    public int getItemCount() {
        return 0;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b\u00a8\u0006\t"}, d2 = {"Lcom/custom/xtreamplayer/ChannelAdapter$ChannelViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "view", "Landroid/view/View;", "(Landroid/view/View;)V", "tvChannelName", "Landroid/widget/TextView;", "getTvChannelName", "()Landroid/widget/TextView;", "app_debug"})
    public static final class ChannelViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @org.jetbrains.annotations.NotNull
        private final android.widget.TextView tvChannelName = null;
        
        public ChannelViewHolder(@org.jetbrains.annotations.NotNull
        android.view.View view) {
            super(null);
        }
        
        @org.jetbrains.annotations.NotNull
        public final android.widget.TextView getTvChannelName() {
            return null;
        }
    }
}