namespace implodingRacoon.Models.Database
{
    public interface IUnitOfWork : IDisposable
    {
        //IUsuarioRepository Usuarios { get; }
        Task<int> SaveAsync();
    }

}
