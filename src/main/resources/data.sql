INSERT INTO COMPANIES (id, name, created_at, updated_at)
VALUES (1, 'TechGlobal Solutions', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO USERS (id, auth0_id, email, name, company_id, created_at, updated_at)
VALUES (1, 'auth0|67f70aa795b0469a2658e7e0', 'plant-manager@mail.com', 'Plant Manager', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO PLANTS (id, name, location, company_id, created_at, updated_at)
VALUES
    (1, 'Planta Buenos Aires', 'Av. Libertador 1280, CABA', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (2, 'Planta Córdoba', 'Av. Colón 5678, Córdoba', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (3, 'Planta Rosario', 'Bv. Oroño 910, Rosario', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO PLANT_USERS (plant_id, user_id)
VALUES
    (1, 1),
    (2, 1),
    (3, 1);
