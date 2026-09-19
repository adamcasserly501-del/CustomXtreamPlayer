package com.custom.xtreamplayer;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0019B?\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\b0\u0007\u0012\u0016\b\u0002\u0010\t\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\b\u0018\u00010\u0007\u00a2\u0006\u0002\u0010\nJ\b\u0010\r\u001a\u00020\u000eH\u0016J\b\u0010\u000f\u001a\u0004\u0018\u00010\fJ\u0018\u0010\u0010\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u000eH\u0016J\u0018\u0010\u0013\u001a\u00020\u00022\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u000eH\u0016J\u0010\u0010\u0017\u001a\u00020\b2\b\u0010\u0018\u001a\u0004\u0018\u00010\fR\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001c\u0010\t\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\b\u0018\u00010\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001a"}, d2 = {"Lcom/custom/xtreamplayer/ChannelAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/custom/xtreamplayer/ChannelAdapter$ChannelViewHolder;", "channels", "", "Lcom/custom/xtreamplayer/ChannelEntity;", "onClick", "Lkotlin/Function1;", "", "onLongClick", "(Ljava/util/List;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)V", "selectedStreamId", "", "getItemCount", "", "getSelectedStreamId", "onBindViewHolder", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "setSelectedChannel", "streamId", "ChannelViewHolder", "app_debug"})
public final class ChannelAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.custom.xtreamplayer.ChannelAdapter.ChannelViewHolder> {
    @org.jetbrains.annotations.NotNull
    private final java.util.List<com.custom.xtreamplayer.ChannelEntity> channels = null;
    @org.jetbrains.annotations.NotNull
    private final kotlin.jvm.functions.Function1<com.custom.xtreamplayer.ChannelEntity, kotlin.Unit> onClick = null;
    @org.jetbrains.annotations.Nullable
    private final kotlin.jvm.functions.Function1<com.custom.xtreamplayer.ChannelEntity, kotlin.Unit> onLongClick = null;
    @org.jetbrains.annotations.Nullable
    private java.lang.String selectedStreamId;
    
    public ChannelAdapter(@org.jetbrains.annotations.NotNull
    java.util.List<com.custom.xtreamplayer.ChannelEntity> channels, @org.jetbrains.annotations.NotNull
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
    
    public final void setSelectedChannel(@org.jetbrains.annotations.Nullable
    java.lang.String streamId) {
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getSelectedStreamId() {
        return null;
    }
    
    @java.lang.Override
    public int getItemCount() {
        return 0;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\r\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\b\u00a8\u0006\u000f"}, d2 = {"Lcom/custom/xtreamplayer/ChannelAdapter$ChannelViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "view", "Landroid/view/View;", "(Landroid/view/View;)V", "favourite", "Landroid/widget/TextView;", "getFavourite", "()Landroid/widget/TextView;", "logo", "Landroid/widget/ImageView;", "getLogo", "()Landroid/widget/ImageView;", "name", "getName", "app_debug"})
    public static final class ChannelViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @org.jetbrains.annotations.NotNull
        private final android.widget.ImageView logo = null;
        @org.jetbrains.annotations.NotNull
        private final android.widget.TextView name = null;
        @org.jetbrains.annotations.NotNull
        private final android.widget.TextView favourite = null;
        
        public ChannelViewHolder(@org.jetbrains.annotations.NotNull
        android.view.View view) {
            super(null);
        }
        
        @org.jetbrains.annotations.NotNull
        public final android.widget.ImageView getLogo() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final android.widget.TextView getName() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final android.widget.TextView getFavourite() {
            return null;
        }
    }
}