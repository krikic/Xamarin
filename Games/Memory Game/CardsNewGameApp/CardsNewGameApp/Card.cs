using Xamarin.Forms;

namespace CardsNewGameApp
{

    public class Card
    {       
        public int CardId { get; set; }
        public int CardValue { get; set; }
        public bool  isSelectedCard { get; set; }   
        public bool  isVisibleImage { get; set; }
      
    }
}
