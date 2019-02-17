using System.ComponentModel;
using System.Runtime.CompilerServices;

namespace AddressBook
{
    public class BaseViewModel : INotifyPropertyChanged
    { 
        public event PropertyChangedEventHandler PropertyChanged; 

        protected void RaisePropertyChanged([CallerMemberName] string propertyName = "")
        {
            PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(propertyName));
        }

    }
}

