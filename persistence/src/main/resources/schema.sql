CREATE TABLE IF NOT EXISTS provinces (
    id SERIAL NOT NULL,
    name TEXT NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS cities (
    id SERIAL NOT NULL,
    name TEXT NOT NULL,
    province_id INT NOT NULL,
    PRIMARY KEY (province_id, name),
    UNIQUE (id),
    CONSTRAINT cities_to_provinces FOREIGN KEY (province_id) REFERENCES provinces (id) ON DELETE CASCADE
);
