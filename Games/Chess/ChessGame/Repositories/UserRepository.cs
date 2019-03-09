using ChessGame.Entities;

namespace ChessGame.Repositories
{
    class UserRepository : BaseRepository<User>
    {
        public UserRepository() : base("users.sqlite")
        {
        }

        public User FindByUsername(string username)
        {
            return Table.Where(u => u.Username == username).FirstOrDefault();
        }

        public User Create(User user)
        {
            Connection.Insert(user);
            return user;
        }

        protected override void Seed()
        {
            if (Table.Where(u => u.IsAdmin).FirstOrDefault() == null)
            {
                Connection.Insert(new User
                {
                    Name = "Admin",
                    Username = "admin",
                    Password = "admin",
                    IsAdmin = true
                });
            }
        }
    }
}