

CREATE TABLE tbl_user
(
	id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
	username VARCHAR(128) NOT NULL,
	password VARCHAR(128) NOT NULL,
	salt VARCHAR(128) NOT NULL,
	email VARCHAR(128) NOT NULL,
	profile TEXT
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE tbl_post
(
	id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
	title VARCHAR(128) NOT NULL,
	content TEXT NOT NULL,
	tags TEXT,
	status INTEGER NOT NULL,
	create_time INTEGER,
	update_time INTEGER,
	author_id INTEGER NOT NULL,
	CONSTRAINT FK_post_author FOREIGN KEY (author_id)
		REFERENCES tbl_user (id) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE tbl_comment
(
	id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
	content TEXT NOT NULL,
	status INTEGER NOT NULL,
	create_time INTEGER,
	author VARCHAR(128) NOT NULL,
	email VARCHAR(128) NOT NULL,
	url VARCHAR(128),
	post_id INTEGER NOT NULL,
	CONSTRAINT FK_comment_post FOREIGN KEY (post_id)
		REFERENCES tbl_post (id) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE tbl_tag
(
	id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(128) NOT NULL,
	frequency INTEGER DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE tbl_procurement (
    ProcurementId INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    ProcurementTitle VARCHAR(512) NOT NULL,
    ProcurementDate date NOT NULL,
    ProcurementKAD varchar(56) NOT NULL,
    ProcurementADA VARCHAR(24) NOT NULL, 
    ProcurementUnit integer NOT NULL,
    ProcurementSigner integer not null,
    ProcurementArProt varchar(128) not null,
    ProcurementFile varchar(256) default null 	
)


CREATE TABLE tbl_contractitem
(
	id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
	quantity INTEGER DEFAULT NULL,
	cost integer DEFAULT NULL,
	description varchar(512) DEFAULT NULL,
	vatid integer default null,
	currencyid integer default null,
	cpvsid varchar(512) default null,
	procurementid varchar(255) default null,
	FOREIGN KEY (procurementid) REFERENCES tbl_procurement (ProcurementId) ,
)

CREATE TABLE tbl_lookup
(
	id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(128) NOT NULL,
	code VARCHAR(128) NOT NULL,
	type VARCHAR(128) NOT NULL,
	position INTEGER NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

INSERT INTO tbl_lookup (name, type, code, position) VALUES ('Draft', 'PostStatus', 1, 1);
INSERT INTO tbl_lookup (name, type, code, position) VALUES ('Published', 'PostStatus', 2, 2);
INSERT INTO tbl_lookup (name, type, code, position) VALUES ('Archived', 'PostStatus', 3, 3);
INSERT INTO tbl_lookup (name, type, code, position) VALUES ('Pending Approval', 'CommentStatus', 1, 1);
INSERT INTO tbl_lookup (name, type, code, position) VALUES ('Approved', 'CommentStatus', 2, 2);
INSERT INTO tbl_lookup (name, type, code, position) VALUES ('23', 'FPA', 23, 1);
INSERT INTO tbl_lookup (name, type, code, position) VALUES ('13', 'FPA', 13, 2);
INSERT INTO tbl_lookup (name, type, code, position) VALUES ('15', 'FPA', 15, 3);
INSERT INTO tbl_lookup (name, type, code, position) VALUES ('Euro', 'Currency', 'euro', 1);
INSERT INTO tbl_lookup (name, type, code, position) VALUES ('US Dollar', 'Currency', 'usd', 2);
INSERT INTO tbl_lookup (name, type, code, position) VALUES ('Υπουργείο ή άλλη εθνική ή ομοσπονδιακή αρχή','contracting_authority', 1, 1);
INSERT INTO tbl_lookup (name, type, code, position) VALUES ('Εθνική ή ομοσπονδιακή υπηρεσία / γραφείο', 'contracting_authority', 2, 2);
INSERT INTO tbl_lookup (name, type, code, position) VALUES ('Περιφερειακή ή τοπική αρχή', 'contracting_authority', 3, 3);
INSERT INTO tbl_lookup (name, type, code, position) VALUES ('Περιφερειακή ή τοπική υπηρεσία / γραφείο', 'contracting_authority', 4, 4);
INSERT INTO tbl_lookup (name, type, code, position) VALUES ('Οργανισμός δημοσίου δικαίου', 'contracting_authority', 5, 5);
INSERT INTO tbl_lookup (name, type, code, position) VALUES ('Ευρωπαϊκό θεσμικό όργανο ή διεθνής οργανισμός', 'contracting_authority', 6, 6);

INSERT INTO tbl_lookup (name, type, code, position) VALUES ('Ανοιχτή', 'award_procedure', 1, 1);
INSERT INTO tbl_lookup (name, type, code, position) VALUES ('Κλειστή', 'award_procedure', 2, 2);
INSERT INTO tbl_lookup (name, type, code, position) VALUES ('Επισπευσμένη κλειστή', 'award_procedure', 3, 3);
INSERT INTO tbl_lookup (name, type, code, position) VALUES ('Διάλογος', 'award_procedure', 4, 4);
INSERT INTO tbl_lookup (name, type, code, position) VALUES ('Ελλάδα', 'country', 'gr', 1);
INSERT INTO tbl_lookup (name, type, code, position) VALUES ('Τουρκία','country', 'tr', 2);
INSERT INTO tbl_lookup (name, type, code, position) VALUES ('Γαλλία', 'country', 'fr', 3);
INSERT INTO tbl_lookup (name, type, code, position) VALUES ('Krithrio 1', 'commissionCriteria', 1, 1);
INSERT INTO tbl_lookup (name, type, code, position) VALUES ('Krithrio 2', 'commissionCriteria', 2, 1);
INSERT INTO tbl_lookup (name, type, code, position) VALUES ('Krithrio 3', 'commissionCriteria', 3, 1);
INSERT INTO tbl_lookup (name, type, code, position) VALUES ('Krithrio 4', 'commissionCriteria', 4, 1);
INSERT INTO tbl_lookup (name, type, code, position) VALUES ('Typos 1', 'contractType', 1, 1);
INSERT INTO tbl_lookup (name, type, code, position) VALUES ('Typos 2', 'contractType', 2, 1);
INSERT INTO tbl_lookup (name, type, code, position) VALUES ('Typos 3', 'contractType', 3, 1);
INSERT INTO tbl_user (username, password, salt, email) VALUES ('demo','2e5c7db760a33498023813489cfadc0b','28b206548469ce62182048fd9cf91760','webmaster@example.com');
INSERT INTO tbl_post (title, content, status, create_time, update_time, author_id, tags) VALUES ('Welcome!','This blog system is developed using Yii. It is meant to demonstrate how to use Yii to build a complete real-world application. Complete source code may be found in the Yii releases.

Feel free to try this system by writing new posts and posting comments.',2,1230952187,1230952187,1,'yii, blog');
INSERT INTO tbl_post (title, content, status, create_time, update_time, author_id, tags) VALUES ('A Test Post', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 2,1230952187,1230952187,1,'test');

INSERT INTO tbl_comment (content, status, create_time, author, email, post_id) VALUES ('This is a test comment.', 2, 1230952187, 'Tester', 'tester@example.com', 2);

INSERT INTO tbl_tag (name) VALUES ('yii');
INSERT INTO tbl_tag (name) VALUES ('blog');
INSERT INTO tbl_tag (name) VALUES ('test');
