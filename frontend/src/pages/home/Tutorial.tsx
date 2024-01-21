import HomeTutorialOne from "@/images/home-tutorial-one.jpg";
import HomeTutorialTwo from "@/images/home-tutorial-two.jpg";
import HomeTutorialThree from "@/images/home-tutorial-three.jpg";
import styles from "./styles.module.scss";
import { Image } from "react-bootstrap";

const Tutorial = () => (
  <div className={styles.tutorialContainer}>
    <div className={styles.tutorialRow}>
      <div className={styles.tutorialText}>
        <p className={styles.tutorialTitle}>Encontrá tu viaje en CABA</p>
        <span className={styles.tutorialDescription}>
          Podrás buscar viajes que se ajusten a tus necesidades. Simplemente,
          ingresá tus puntos de partida y llegada, la fecha y la hora, y te
          mostraremos los viajes disponibles que se ajusten a tu horario.
          Además, podrás filtrar por precio e incluso, si lo necesitás realizar
          todas las semanas, podrás buscar
          <span className="secondary-text ms-1 fw-semibold">
            viajes recurrentes
          </span>
          .
        </span>
      </div>
      <div className={styles.tutorialImage}>
        <Image src={HomeTutorialOne} alt="Buscar viajes" />
      </div>
    </div>
    <hr className="secondary-text" />
    <div className={styles.tutorialRow}>
      <div className={styles.tutorialText}>
        <p className={styles.tutorialTitle}>Reservá tu viaje</p>
        <p className={styles.tutorialDescription}>
          Cuando tengas decidido la mejor opción, hacé click en el botón
          "Reservar". Una vez que se confirme tu reserva, enviaremos un correo
          electrónico tanto a ti como al conductor con la información de
          contacto del otro. Esto hace que sea fácil coordinar los detalles de
          tu viaje y garantiza una experiencia fluida tanto para ti como para el
          conductor.
        </p>
      </div>
      <div className={styles.tutorialImage}>
        <Image src={HomeTutorialTwo} alt="Reservar viajes" />
      </div>
    </div>
    <hr className="secondary-text" />
    <div className={styles.tutorialRow}>
      <div className={styles.tutorialText}>
        <p className={styles.tutorialTitle}>Manejá y compartí</p>
        <p className={styles.tutorialDescription}>
          No solo podrás encontrar viajes, sino que también podrás convertirte
          en conductor y compartir tus propios viajes. Solo necesitarás agregar
          la información de tu auto y ya podrás ofrecer viajes a otros.
          Establecé tu propio horario y ruta, y los usuarios podrán unirse a tu
          viaje si se ajusta a sus necesidades de viaje. De esta manera, estarás
          ayudando a reducir el tráfico y las emisiones, al mismo tiempo que
          ahorrarás dinero en gasolina y peajes.
        </p>
      </div>
      <div className={styles.tutorialImage}>
        <Image src={HomeTutorialThree} alt="Crear viajes" />
      </div>
    </div>
  </div>
);

export default Tutorial;
