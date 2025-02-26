namespace implodingRacoon.Models
{
    public interface IUnitOfWork : IDisposable
    {
        //IUsuarioRepository Usuarios { get; }
        Task<int> SaveAsync();
    }

}
