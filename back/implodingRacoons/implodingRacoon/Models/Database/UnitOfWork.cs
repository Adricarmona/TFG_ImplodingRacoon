using implodingRacoon.Models.Database.Repository;

namespace implodingRacoon.Models.Database
{
    public class UnitOfWork : IUnitOfWork
    {
        private readonly ImplodingRacoonsContext _context;

        private UsuarioRepository _usuarioRepository;

        public UsuarioRepository UsuarioRepository => _usuarioRepository ??= new UsuarioRepository(_context);

        public UnitOfWork(ImplodingRacoonsContext context)
        {
            _context = context;
        }

        public async Task<int> SaveAsync()
        {
            return await _context.SaveChangesAsync();
        }

        public void Dispose()
        {
            _context.Dispose();
        }
    }
}
