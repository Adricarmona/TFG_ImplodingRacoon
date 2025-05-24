using implodingRacoon.Models.Database.Entities;
using implodingRacoon.Models.Database.Repository.Repository;

namespace implodingRacoon.Models.Database.Repository
{
    public class ComentarioRepository : Repository<Comentario, int>
    {
        public ComentarioRepository(ImplodingRacoonsContext context) : base(context) { }
    }
}