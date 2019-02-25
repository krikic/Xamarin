package md506447919c590fa09f25dfea633c87ab3;


public class DashboardRenderer_ScaleListener
	extends android.view.ScaleGestureDetector.SimpleOnScaleGestureListener
	implements
		mono.android.IGCUserPeer
{
/** @hide */
	public static final String __md_methods;
	static {
		__md_methods = 
			"n_onScale:(Landroid/view/ScaleGestureDetector;)Z:GetOnScale_Landroid_view_ScaleGestureDetector_Handler\n" +
			"";
		mono.android.Runtime.register ("MindFusion.Charting.DashboardRenderer+ScaleListener, MindFusion.Charting.Android", DashboardRenderer_ScaleListener.class, __md_methods);
	}


	public DashboardRenderer_ScaleListener ()
	{
		super ();
		if (getClass () == DashboardRenderer_ScaleListener.class)
			mono.android.TypeManager.Activate ("MindFusion.Charting.DashboardRenderer+ScaleListener, MindFusion.Charting.Android", "", this, new java.lang.Object[] {  });
	}

	public DashboardRenderer_ScaleListener (md506447919c590fa09f25dfea633c87ab3.DashboardRenderer p0)
	{
		super ();
		if (getClass () == DashboardRenderer_ScaleListener.class)
			mono.android.TypeManager.Activate ("MindFusion.Charting.DashboardRenderer+ScaleListener, MindFusion.Charting.Android", "MindFusion.Charting.DashboardRenderer, MindFusion.Charting.Android", this, new java.lang.Object[] { p0 });
	}


	public boolean onScale (android.view.ScaleGestureDetector p0)
	{
		return n_onScale (p0);
	}

	private native boolean n_onScale (android.view.ScaleGestureDetector p0);

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
