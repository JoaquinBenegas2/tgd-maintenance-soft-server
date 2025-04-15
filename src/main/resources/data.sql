INSERT INTO COMPANIES (id, name, created_at, updated_at)
VALUES (1, 'TechGlobal Solutions', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO USERS (id, auth0_id, email, name, role, image, company_id,  created_at, updated_at)
VALUES
    (1, 'auth0|67f70aa795b0469a2658e7e0', 'plant-manager@mail.com', 'Plant Manager', 'PLANT_MANAGER', 'https://s.gravatar.com/avatar/dde9f4169bcf675751b4b4da24682956?s=480&r=pg&d=https%3A%2F%2Fcdn.auth0.com%2Favatars%2Fpl.png', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (2, 'auth0|67fdd4ededfc67117cfdd43b', 'plant-supervisor@mail.com', 'Plant Supervisor', 'PLANT_SUPERVISOR', 'https://s.gravatar.com/avatar/66d2486781c277b4d4d00acb2c599130?s=480&r=pg&d=https%3A%2F%2Fcdn.auth0.com%2Favatars%2Fpl.png', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (3, 'auth0|67fdd5a0c9b41c726c98cd6d', 'plant-operator@mail.com', 'Plant Operator', 'PLANT_OPERATOR', 'https://s.gravatar.com/avatar/66d2486781c277b4d4d00acb2c599130?s=480&r=pg&d=https%3A%2F%2Fcdn.auth0.com%2Favatars%2Fpl.png', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

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
