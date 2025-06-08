import { Carta } from "./carta";
import { UserGame } from "./user-game";

export interface Mesa {
    IdMesa: number,
    jugadores: UserGame[]
    BarajaMesa: Carta[]
    CartasLanzadas: Carta[]
}
