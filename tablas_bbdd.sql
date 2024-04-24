CREATE TABLE propietarios
(
    id_prop     INT(11) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT,
    nombre_prop VARCHAR(100),
    dni_prop    VARCHAR(9),
    PRIMARY KEY (id_prop)
)
    COMMENT ='Tabla con propietarios de vehículos'
    ENGINE = InnoDB
    COLLATE = 'latin1_swedish_ci';

CREATE TABLE vehiculos
(
    mat_veh    CHAR(7) NOT NULL,
    marca_veh  VARCHAR(50),
    kms_veh    INT(11) UNSIGNED ZEROFILL,
    precio_veh FLOAT UNSIGNED ZEROFILL,
    desc_veh   VARCHAR(300),
    id_prop    INT(11) UNSIGNED ZEROFILL,
    PRIMARY KEY (mat_veh),
    INDEX (id_prop),
    FOREIGN KEY (id_prop)
        REFERENCES propietarios (id_prop)
        ON DELETE CASCADE -- Asegura que el borrado sea posible
)
    COMMENT ='Tabla que contiene vehiculos'
    ENGINE = InnoDB
    COLLATE = 'latin1_swedish_ci';
