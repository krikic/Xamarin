using System.ComponentModel;
using Xamarin.Forms;

namespace CardsNewGameApp
{
    //That the class implements the INotifyPropertyChanged interface, 
    //which will allow elements of the user interface automatically sync 
    //values with the values of the object properties card.
    public class CardViewModel : INotifyPropertyChanged
    {
         public event PropertyChangedEventHandler PropertyChanged;

        public Card card { get; set; }

        public CardViewModel()
        {
            card = new Card();
        }

       

        public int CardId
        {
            get { return card.CardId; }
            set
            {
                card.CardId = value;
                onPropertyChanged("CardId");
            }
        }

        public int CardValue
        {
            get { return card.CardValue; }
            set
            {
                card.CardValue = value;
                onPropertyChanged("CardValue");
            }
        }
        public bool IsVisibleImage
        {
            get { return card.isVisibleImage; }
            set
            {
                card.isVisibleImage = value;
                onPropertyChanged("IsVisibleImage");
            }
        }
        public bool IsSelectedCard
        {
            get { return card.isSelectedCard; }
            set
            {
                card.isSelectedCard = value;
                onPropertyChanged("IsSelectedCard");
            }
        }
        public void onPropertyChanged(string prop = "")
        {
            PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(prop));

        }
    }
   }