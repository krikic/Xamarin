using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CardsNewGameApp
{
    public interface ICardsSound
    {
        void ShowToast(string message);
        void Vibration(string file);
    }
}
