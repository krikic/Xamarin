using System;
using System.Collections.Generic;

using Xamarin.Forms;

namespace NavigationTransitions
{
    public partial class ThirdPage : ContentPage
    {
        void Handle_Back_Clicked(object sender, System.EventArgs e)
        {
            Navigation.PopAsync();
        }

        void Calculated(object sender, System.EventArgs e){
        
                if (txtValor1.Text != null || txtValor2.Text != null)
                {
                    txtResultado.Text = GetSustract(Convert.ToInt32(txtValor1.Text), Convert.ToInt32(txtValor2.Text)).ToString();
                }
            }
           

        public int GetSustract(int ValueOne, int ValueTwo)
        {
            return ValueOne - ValueTwo;
        }
        

        public ThirdPage()
        {
            InitializeComponent();
            this.BindingContext = new ThirdPageModel();

        }
    }
}
