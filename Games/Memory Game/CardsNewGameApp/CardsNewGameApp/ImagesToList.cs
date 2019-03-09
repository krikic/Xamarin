using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Xamarin.Forms;

namespace CardsNewGameApp
{
    public class ImagesToList
    {
        public int row { get; set; }
        public int col { get; set; }
        public int id { get; set; }
        public Image image { get; set; }
        public TapGestureRecognizer tapImage { get; set; }


    }
}
