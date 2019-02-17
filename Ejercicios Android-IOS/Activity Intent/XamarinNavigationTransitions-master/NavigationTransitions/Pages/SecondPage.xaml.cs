using System;
using System.Collections.Generic;

using Xamarin.Forms;

namespace NavigationTransitions
{
    public partial class SecondPage : ContentPage
    {
        void Handle_Back_Clicked(object sender, System.EventArgs e)
        {
            Navigation.PopAsync();
        }
         void Calculated(object sender, System.EventArgs e){
       
                if (txtValor1.Text != null || txtValor2.Text != null)
                {
                    txtResultado.Text = GetSum(Convert.ToInt32(txtValor1.Text), Convert.ToInt32(txtValor2.Text)).ToString();
                }
            }
           
        

        public int GetSum(int ValueOne, int ValueTwo)
        {
            return ValueOne + ValueTwo;
        }
        public SecondPage()
        {
            InitializeComponent();
            this.BindingContext = new SecondPageModel();
        }
    }
}

