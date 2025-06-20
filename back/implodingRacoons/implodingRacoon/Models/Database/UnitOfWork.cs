﻿using implodingRacoon.Models.Database.Entities;
using implodingRacoon.Models.Database.Repository;

namespace implodingRacoon.Models.Database
{
    public class UnitOfWork : IUnitOfWork
    {
        private readonly ImplodingRacoonsContext _context;

        private UsuarioRepository _usuarioRepository;
        private CartaRepository _cartaRepository;
        private SolicitudAmistadRepository _solicitudAmistadRepository;
        private PublicacionRepository _publicacionRepository;
        private ComentarioRepository _comentarioRepository;

        public UsuarioRepository UsuarioRepository => _usuarioRepository ??= new UsuarioRepository(_context);
        public CartaRepository CartaRepository => _cartaRepository ??= new CartaRepository(_context);
        public SolicitudAmistadRepository SolicitudAmistadRepository => _solicitudAmistadRepository ??= new SolicitudAmistadRepository(_context);
        public PublicacionRepository PublicacionRepository => _publicacionRepository ??= new PublicacionRepository(_context);
        public ComentarioRepository ComentarioRepository => _comentarioRepository ??= new ComentarioRepository(_context);

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
