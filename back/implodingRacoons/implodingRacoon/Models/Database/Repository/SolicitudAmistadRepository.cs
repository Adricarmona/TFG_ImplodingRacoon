using implodingRacoon.Models.Database.Entities;
using implodingRacoon.Models.Database.Repository.Repository;

namespace implodingRacoon.Models.Database.Repository
{
    public class SolicitudAmistadRepository : Repository<SolicitudAmistad, int>
    {
        public SolicitudAmistadRepository(ImplodingRacoonsContext context) : base(context) { }
    }
}
