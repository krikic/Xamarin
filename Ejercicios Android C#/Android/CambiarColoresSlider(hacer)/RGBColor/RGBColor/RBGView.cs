
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Android.App;
using Android.Content;
using Android.OS;
using Android.Runtime;
using Android.Views;
using Android.Widget;

namespace RGBColor
{
    [Activity(Label = "RBGView")]
    public class RBGView : Activity
    {
        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);

            // Create your application here
        }

        public class RgbView extends View
        {

            Paint p=new Paint();
        public RgbView(Context context)
        {
            super(context);
            init(null, 0);
        }

        public RgbView(Context context, AttributeSet attrs)
        {
            super(context, attrs);
            init(attrs, 0);
        }

        public RgbView(Context context, AttributeSet attrs, int defStyle)
        {
            super(context, attrs, defStyle);
            init(attrs, defStyle);
        }

        private void init(AttributeSet attrs, int defStyle)
        {
            p.setColor(Color.RED);
        }

        public void setColor(int Red, int Green, int Blue)
        {

            p.setARGB(255, Red, Green, Blue);
            invalidate();

        }

        @Override
    protected void onDraw(Canvas canvas)
        {
            super.onDraw(canvas);

            canvas.drawRect(0, 0, getHeight(), getWidth(), p);
        }




    }
}
