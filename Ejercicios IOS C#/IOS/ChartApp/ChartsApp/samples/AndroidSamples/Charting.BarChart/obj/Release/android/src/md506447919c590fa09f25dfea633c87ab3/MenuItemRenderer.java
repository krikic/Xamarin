package md506447919c590fa09f25dfea633c87ab3;


public class MenuItemRenderer
	extends md51558244f76c53b6aeda52c8a337f2c37.ImageRenderer
	implements
		mono.android.IGCUserPeer
{
/** @hide */
	public static final String __md_methods;
	static {
		__md_methods = 
			"n_draw:(Landroid/graphics/Canvas;)V:GetDraw_Landroid_graphics_Canvas_Handler\n" +
			"";
		mono.android.Runtime.register ("MindFusion.Charting.MenuItemRenderer, MindFusion.Charting.Android", MenuItemRenderer.class, __md_methods);
	}


	public MenuItemRenderer (android.content.Context p0)
	{
		super (p0);
		if (getClass () == MenuItemRenderer.class)
			mono.android.TypeManager.Activate ("MindFusion.Charting.MenuItemRenderer, MindFusion.Charting.Android", "Android.Content.Context, Mono.Android", this, new java.lang.Object[] { p0 });
	}


	public MenuItemRenderer (android.content.Context p0, android.util.AttributeSet p1)
	{
		super (p0, p1);
		if (getClass () == MenuItemRenderer.class)
			mono.android.TypeManager.Activate ("MindFusion.Charting.MenuItemRenderer, MindFusion.Charting.Android", "Android.Content.Context, Mono.Android:Android.Util.IAttributeSet, Mono.Android", this, new java.lang.Object[] { p0, p1 });
	}


	public MenuItemRenderer (android.content.Context p0, android.util.AttributeSet p1, int p2)
	{
		super (p0, p1, p2);
		if (getClass () == MenuItemRenderer.class)
			mono.android.TypeManager.Activate ("MindFusion.Charting.MenuItemRenderer, MindFusion.Charting.Android", "Android.Content.Context, Mono.Android:Android.Util.IAttributeSet, Mono.Android:System.Int32, mscorlib", this, new java.lang.Object[] { p0, p1, p2 });
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
