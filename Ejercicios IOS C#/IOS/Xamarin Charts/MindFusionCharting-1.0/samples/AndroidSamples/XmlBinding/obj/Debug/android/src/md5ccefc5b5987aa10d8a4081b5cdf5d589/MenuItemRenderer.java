package md5ccefc5b5987aa10d8a4081b5cdf5d589;


public class MenuItemRenderer
	extends md5b60ffeb829f638581ab2bb9b1a7f4f3f.ImageRenderer
	implements
		mono.android.IGCUserPeer
{
/** @hide */
	public static final String __md_methods;
	static {
		__md_methods = 
			"n_draw:(Landroid/graphics/Canvas;)V:GetDraw_Landroid_graphics_Canvas_Handler\n" +
			"";
		mono.android.Runtime.register ("MindFusion.Charting.MenuItemRenderer, MindFusion.Charting.Android, Version=1.0.6122.24748, Culture=neutral, PublicKeyToken=null", MenuItemRenderer.class, __md_methods);
	}


	public MenuItemRenderer (android.content.Context p0) throws java.lang.Throwable
	{
		super (p0);
		if (getClass () == MenuItemRenderer.class)
			mono.android.TypeManager.Activate ("MindFusion.Charting.MenuItemRenderer, MindFusion.Charting.Android, Version=1.0.6122.24748, Culture=neutral, PublicKeyToken=null", "Android.Content.Context, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=84e04ff9cfb79065", this, new java.lang.Object[] { p0 });
	}


	public MenuItemRenderer (android.content.Context p0, android.util.AttributeSet p1) throws java.lang.Throwable
	{
		super (p0, p1);
		if (getClass () == MenuItemRenderer.class)
			mono.android.TypeManager.Activate ("MindFusion.Charting.MenuItemRenderer, MindFusion.Charting.Android, Version=1.0.6122.24748, Culture=neutral, PublicKeyToken=null", "Android.Content.Context, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=84e04ff9cfb79065:Android.Util.IAttributeSet, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=84e04ff9cfb79065", this, new java.lang.Object[] { p0, p1 });
	}


	public MenuItemRenderer (android.content.Context p0, android.util.AttributeSet p1, int p2) throws java.lang.Throwable
	{
		super (p0, p1, p2);
		if (getClass () == MenuItemRenderer.class)
			mono.android.TypeManager.Activate ("MindFusion.Charting.MenuItemRenderer, MindFusion.Charting.Android, Version=1.0.6122.24748, Culture=neutral, PublicKeyToken=null", "Android.Content.Context, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=84e04ff9cfb79065:Android.Util.IAttributeSet, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=84e04ff9cfb79065:System.Int32, mscorlib, Version=2.0.5.0, Culture=neutral, PublicKeyToken=7cec85d7bea7798e", this, new java.lang.Object[] { p0, p1, p2 });
	}


	public void draw (android.graphics.Canvas p0)
	{
		n_draw (p0);
	}

	private native void n_draw (android.graphics.Canvas p0);

	private java.util.ArrayList refList;
	public void monodroidAddReference (java.lang.Object obj)
	{
		if (refList == null)
			refList = new java.util.ArrayList ();
		refList.add (obj);
	}

	public void monodroidClearReferences ()
	{
		if (refList != null)
			refList.clear ();
	}
}
