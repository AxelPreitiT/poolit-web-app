import { Button, Form, Image, InputGroup } from "react-bootstrap";
import styles from "./styles.module.scss";
import FormError from "@/components/forms/FormError/FormError";
import { BiCheck } from "react-icons/bi";
import { FaDollarSign } from "react-icons/fa6";
import {
  BsArrowRepeat,
  BsCalendarFill,
  BsCarFrontFill,
  BsClockFill,
  BsFillPeopleFill,
  BsGeoAlt,
  BsGeoAltFill,
  BsInfoCircleFill,
} from "react-icons/bs";
import { FaCaretRight } from "react-icons/fa";
import RegisterBanner from "@/images/register-banner.jpg";

const CreateTripPage = () => {
  return (
    <div className={styles.mainContainer}>
      <div className={styles.containerHeader}>
        <h1 className="secondary-text">Create a trip</h1>
        <hr className="secondary-text" />
      </div>
      <div>
        <Form className={styles.form}>
          <div className={styles.formGroup} id="origin">
            <div className={styles.formHeader}>
              <BsGeoAlt className="h2 secondary-text" />
              <h2 className="secondary-text">Origin</h2>
            </div>
            <div className={styles.formContainer}>
              <div className={styles.formRow}>
                <div className={styles.formItem}>
                  <Form.Select size="sm">
                    <option>District</option>
                  </Form.Select>
                  <FormError error="error" className={styles.formItemError} />
                </div>
                <div className={styles.formItem}>
                  <Form.Control type="text" placeholder="Address" size="sm" />
                  <FormError error="error" className={styles.formItemError} />
                </div>
              </div>
              <div className={styles.formRow}>
                <div className={styles.formItem}>
                  <InputGroup size="sm" className={styles.formItemGroup}>
                    <Button className="secondary-btn">
                      <BsCalendarFill className="light-text" />
                    </Button>
                    <Form.Control type="text" placeholder="Date" />
                  </InputGroup>
                  <FormError error="error" className={styles.formItemError} />
                </div>
                <div className={styles.formItem}>
                  <InputGroup size="sm" className={styles.formItemGroup}>
                    <Button className="secondary-btn">
                      <BsClockFill className="light-text" />
                    </Button>
                    <Form.Control type="text" placeholder="Time" />
                  </InputGroup>
                  <FormError error="error" className={styles.formItemError} />
                </div>
              </div>
              <div className={styles.formRow}>
                <div className={styles.recurrentCheckbox}>
                  <Form.Check type="checkbox" />
                  <label className="light-text">Recurrent trip</label>
                </div>
              </div>
              <div className={styles.formRow}>
                <div className={styles.recurrentLabel}>
                  <BsArrowRepeat className="secondary-text" />
                  <span className="secondary-text">Every</span>
                  <strong className="secondary-text">Monday</strong>
                  <span className="secondary-text">until</span>
                </div>
                <div className={styles.formItem}>
                  <InputGroup size="sm" className={styles.formItemGroup}>
                    <Button className="secondary-btn">
                      <BsCalendarFill className="light-text" />
                    </Button>
                    <Form.Control type="text" placeholder="Last date" />
                  </InputGroup>
                  <FormError error="error" className={styles.formItemError} />
                </div>
              </div>
            </div>
          </div>
          <div className={styles.verticalDottedLine}></div>
          <div className={styles.formGroup} id="destination">
            <div className={styles.formHeader}>
              <BsGeoAltFill className="h2 secondary-text" />
              <h2 className="secondary-text">Destination</h2>
            </div>
            <div className={styles.formContainer}>
              <div className={styles.formRow}>
                <div className={styles.formItem}>
                  <Form.Select size="sm">
                    <option>District</option>
                  </Form.Select>
                  <FormError error="error" className={styles.formItemError} />
                </div>
                <div className={styles.formItem}>
                  <Form.Control type="text" placeholder="Address" size="sm" />
                  <FormError error="error" className={styles.formItemError} />
                </div>
              </div>
            </div>
          </div>
          <div className={styles.formGroup} id="details">
            <div className={styles.formHeader}>
              <BsInfoCircleFill className="h2 secondary-text" />
              <h2 className="secondary-text">Details</h2>
            </div>
            <div className={styles.formContainer}>
              <div className={styles.formRow}>
                <div className={styles.formItem}>
                  <InputGroup size="sm" className={styles.formItemGroup}>
                    <InputGroup.Text>
                      <BsCarFrontFill className="text" />
                    </InputGroup.Text>
                    <Form.Select size="sm">
                      <option>Select car</option>
                    </Form.Select>
                  </InputGroup>
                  <div className={styles.carInfoContainer}>
                    <div className={styles.carInfoItem}>
                      <FaCaretRight className="light-text" />
                      <span className="light-text">Brand: Ford</span>
                    </div>
                    <hr className="light-text" />
                    <div className={styles.carInfoItem}>
                      <FaCaretRight className="light-text" />
                      <span className="light-text">License plate: OIC319</span>
                    </div>
                  </div>
                  <FormError error="error" className={styles.formItemError} />
                </div>
                <div className={styles.formItem}>
                  <Image
                    rounded
                    fluid
                    className={styles.carImage}
                    src={RegisterBanner}
                  />
                </div>
              </div>
              <div className={styles.formRow}>
                <div className={styles.formItem}>
                  <InputGroup size="sm" className={styles.formItemGroup}>
                    <InputGroup.Text>
                      <BsFillPeopleFill className="text" />
                    </InputGroup.Text>
                    <Form.Control type="text" placeholder="Number of seats" />
                  </InputGroup>
                  <FormError error="error" className={styles.formItemError} />
                </div>
              </div>
              <div className={styles.formRow}>
                <div className={styles.formItem}>
                  <InputGroup size="sm" className={styles.formItemGroup}>
                    <InputGroup.Text>
                      <FaDollarSign className="text" />
                    </InputGroup.Text>
                    <Form.Control type="text" placeholder="Price per trip" />
                    <InputGroup.Text>ARS</InputGroup.Text>
                  </InputGroup>
                  <FormError error="error" className={styles.formItemError} />
                </div>
              </div>
            </div>
          </div>
          <div className={styles.submitContainer}>
            <Button type="submit" className="secondary-btn">
              <BiCheck className="light-text h2" />
              <span className="light-text h3">Create</span>
            </Button>
          </div>
        </Form>
      </div>
    </div>
  );
};

export default CreateTripPage;
