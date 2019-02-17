using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Xamarin.Forms;

namespace Xamarin.Calculadora
{
    public partial class MainPage : ContentPage
    {
        public MainPage()
        {
            InitializeComponent();

            btnCalcular.Clicked += onClicked;

        }

        void onClicked(object sender, EventArgs args)
        {

            try
            {
                if (txtValor1.Text != null || txtValor2.Text != null)
                {
                    txtResultado.Text = GetSum(Convert.ToInt32(txtValor1.Text), Convert.ToInt32(txtValor2.Text)).ToString();
                }
            }
            catch (Exception e)
            {
               
                throw;
            }
        }

        public int GetSum(int ValueOne, int ValueTwo)
        {
            return ValueOne + ValueTwo;
        }
    }
}
