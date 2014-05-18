------------
-- sites --  Ishag 
------------
    
insert into sites (id, section_id, office_hours, lecture_hours, lecture_room) values
    (1, 1000300, 'MW 1-3pm and TR 2-4pm, in E&T A317', 'TR 4:20pm - 6:00pm', 'E&T A210');

insert into blocks(id, site_id, name) values 
    (1, 1, 'Lecture Notes');
     
insert into items(id, name, url_content, block_id, item_type, item_order) values
    (1, 'Setting Up Development Enviroment', 'http://csns.calstatela.edu/wiki/content/cysun/course_materials/eclipse', 1, 2, 1);
    