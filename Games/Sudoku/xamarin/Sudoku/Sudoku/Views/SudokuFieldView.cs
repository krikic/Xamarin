using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Android.App;
using Android.Content;
using Android.OS;
using Android.Runtime;
using Android.Util;
using Android.Views;
using Android.Widget;
using Android.Graphics;

namespace Sudoku
{
	public class SudokuFieldView : View
	{
		public Rect picker;

		public SudokuFieldView (Context context) :
			base (context)
		{
			Initialize ();
		}

		public SudokuFieldView (Context context, IAttributeSet attrs) :
			base (context, attrs)
		{
			Initialize ();
		}

		public SudokuFieldView (Context context, IAttributeSet attrs, int defStyle) :
			base (context, attrs, defStyle)
		{
			Initialize ();
		}

		public SudokuFieldView(IntPtr javaReference, JniHandleOwnership transfer)
			: base(javaReference, transfer) { }

		void Initialize ()
		{
			picker = new Rect();
			this.SetOnTouchListener(new TouchListener(this));
		}
			
		protected override void OnMeasure(int widthMeasureSpec, int heightMeasureSpec) {
			base.OnMeasure(widthMeasureSpec, heightMeasureSpec);

			int height = MeasuredHeight;
			int width = MeasuredWidth;
			if (height < width){
				SetMeasuredDimension(height, height);
			} else {
				SetMeasuredDimension(width, width);
			}

		}
			
		protected override void OnDraw(Canvas canvas) {
			Paint p = new Paint();
			Paint n = new Paint();
			n.Color = (Color.Black);
			n.TextAlign = (Paint.Align.Center);
			p.Color = (Color.Blue);
			p.Alpha = (50);
			canvas.DrawRect(picker, p);
			int[,] field = GameController.getInstance().getNumbers();
			float widthStep = (float)MeasuredWidth/9;
			float heightStep = (float)MeasuredHeight/9;
			n.TextSize = (heightStep);
			int[,] initial = GameController.getInstance().getInitialNumber();
			for (int i = 0; i < 9; i++){
				for (int q = 0; q < 9; q++){
					if (initial[i,q] != 0){
						canvas.DrawText(initial[i,q] + "", widthStep*q + (widthStep / 2),
							heightStep*i + heightStep - (heightStep*0.1f), n);
					}
				}
			}
			n.Alpha = (150);
			for (int i = 0; i < 9; i++){
				for (int q = 0; q < 9; q++){
					if (field[i,q] != 0){
						canvas.DrawText(field[i,q] + "", widthStep*i + (widthStep / 2),
							heightStep*q + heightStep - (heightStep*0.1f), n);
					}
				}
			}

			base.OnDraw(canvas);
		}

		public void onDigitsPress(int num){
			GameController controller = GameController.getInstance();
			controller.setCell(num);
			Invalidate();
		}

		class TouchListener : Java.Lang.Object, IOnTouchListener
		{
			private SudokuFieldView container;
			public TouchListener(SudokuFieldView container){
				this.container = container;
			}

			public bool OnTouch (View v, MotionEvent e)
			{
				if (e.Action == MotionEventActions.Down) {
					GameController controller = GameController.getInstance();
					double widthStep = (double)v.MeasuredWidth/9;
					double heightStep = (double)v.MeasuredHeight/9;
					int x = (int)((e.GetX())/widthStep);
					int y = (int)((e.GetY())/heightStep);
					controller.touch(x, y);
					IntPoint pickerPoint = controller.getActive();
					if (pickerPoint.getX() == -1){
						container.picker.Set(0, 0, 0, 0);
					} else {
						container.picker.Set((int)(x * widthStep), (int)(y * heightStep),
							(int)(x * widthStep + widthStep), (int)(y * heightStep + heightStep));
					}
					container.Invalidate();
				}
				return true;
			}

		}
	}
}

