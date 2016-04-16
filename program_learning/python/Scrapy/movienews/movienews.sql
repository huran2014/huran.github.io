use stczwd;

create table movienews(
	id int auto_increment not null primary key ,
	title longtext,
	url char(255),
        image char(255),
	src char(255),
	pdate char(255),
	content longtext );
	
