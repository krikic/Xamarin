using ChessGame.Entities;
using ChessGame.Repositories;

namespace ChessGame.Services
{
    class AuthenticationService
    {
        private UserRepository Repository { get; }

        public AuthenticationService(UserRepository repository)
        {
            Repository = repository;
        }

        public User LoggedUser { get; private set; }

        public User LogIn(string username, string password)
        {
            if (LoggedUser != null)
            {
                return LoggedUser;
            }

            var user = Repository.FindByUsername(username);

            if (user == null || user.Password != password)
            {
                return null;
            }

            LoggedUser = user;
            return user;
        }

        public void LogOut()
        {
            LoggedUser = null;
        }

        public User GetUserByUsername(string username)
        {
            return Repository.FindByUsername(username);
        }

        private User GetUser(string username, string password)
        {
            var user = Repository.FindByUsername(username);

            return user == null || user.Password != password ? null : user;
        }

        public bool Register(User user)
        {
            user = Repository.Create(user);
            return user != null;
        }
    }
}