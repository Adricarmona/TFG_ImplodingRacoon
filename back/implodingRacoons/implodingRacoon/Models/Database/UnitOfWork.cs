using implodingRacoon.Models.Database.Repository;

namespace implodingRacoon.Models.Database
{
    public class UnitOfWork : IUnitOfWork
    {
        private readonly ImplodingRacoonsContext _context;

        private UsuarioRepository _usuarioRepository;
        private CartaRepository _cartaRepository;
        private SolicitudAmistadRepository _solicitudAmistadRepository;

        public UsuarioRepository UsuarioRepository => _usuarioRepository ??= new UsuarioRepository(_context);
        public CartaRepository CartaRepository => _cartaRepository ??= new CartaRepository(_context);
        public SolicitudAmistadRepository SolicitudAmistadRepository => _solicitudAmistadRepository ??= new SolicitudAmistadRepository(_context);

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
