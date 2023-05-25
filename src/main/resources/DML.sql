INSERT INTO PLOT (area, address, slope_angle, is_irrigated) VALUES (42, 'Hassan Sadek St.',12, 0);
INSERT INTO PLOT (area, address, slope_angle, is_irrigated) VALUES (58, 'Obour',19, 0);
INSERT INTO PLOT (area, address, slope_angle, is_irrigated) VALUES (15, 'Dawlatian St.',3, 0);
INSERT INTO PLOT (area, address, slope_angle, is_irrigated) VALUES (22, 'Kornich St.',0, 0);


INSERT INTO UTILITY (UTILITY_ID, SERVICE, ID) VALUES (1,'Internet', 1);
INSERT INTO UTILITY (UTILITY_ID, SERVICE, ID) VALUES (2,'Automatic Lighting', 1);
INSERT INTO UTILITY (UTILITY_ID, SERVICE, ID) VALUES (3,'Automatic Lighting', 2);
INSERT INTO UTILITY (UTILITY_ID, SERVICE, ID) VALUES (4,'Internet', 2);
INSERT INTO UTILITY (UTILITY_ID, SERVICE, ID) VALUES (5,'Internet', 3);
INSERT INTO UTILITY (UTILITY_ID, SERVICE, ID) VALUES (6,'Internet', 4);


INSERT INTO PLOT_CONFIGURATION (ID, TIME_SLOT, WATER_AMOUNT) VALUES (1, '0 * * * * *', 20);
INSERT INTO PLOT_CONFIGURATION (ID, TIME_SLOT, WATER_AMOUNT) VALUES (2, '0 * * * * *', 20);
INSERT INTO PLOT_CONFIGURATION (ID, TIME_SLOT, WATER_AMOUNT) VALUES (3, '0 * * * * *', 20);
