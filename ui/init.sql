CREATE SCHEMA DATACORE_UI
    AUTHORIZATION POSTGRES;


-- 코드 그룹 기본 테이블 스키마
CREATE TABLE DATACORE_UI.CODE_GROUP_BASE
(
    CODE_GROUP_ID       VARCHAR(10) NOT NULL,
    CODE_GROUP_NAME     VARCHAR(50) NOT NULL,
    DESCRIPTION         VARCHAR(500),
    ENABLED             BOOLEAN NOT NULL DEFAULT TRUE,
    CREATE_DATETIME     TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CREATOR_ID          VARCHAR(64),
    MODIFY_DATETIME     TIMESTAMP WITHOUT TIME ZONE,
    MODIFIER_ID         VARCHAR(64),
    CONSTRAINT CODE_GROUP_BASE_PK PRIMARY KEY (CODE_GROUP_ID)
);


-- 코드 기본 테이블 스키마
CREATE TABLE DATACORE_UI.CODE_BASE
(
    CODE_GROUP_ID       VARCHAR(10) NOT NULL,
    CODE_ID             VARCHAR(30) NOT NULL,
    LANG_CD				VARCHAR(5) NOT NULL DEFAULT 'en',
    CODE_NAME           VARCHAR(50) NOT NULL,
    SORT_ORDER          NUMERIC(5) NOT NULL,
    ENABLED             BOOLEAN NOT NULL DEFAULT TRUE,
    DESCRIPTION         VARCHAR(500),
    CREATE_DATETIME     TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CREATOR_ID          VARCHAR(64),
    MODIFY_DATETIME     TIMESTAMP WITHOUT TIME ZONE,
    MODIFIER_ID         VARCHAR(64),
    CONSTRAINT CODE_BASE_PK PRIMARY KEY (CODE_GROUP_ID, CODE_ID, LANG_CD),
    CONSTRAINT CODE_BASE_FK01 FOREIGN KEY (CODE_GROUP_ID)
    REFERENCES DATACORE_UI.CODE_GROUP_BASE (CODE_GROUP_ID) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
);

CREATE INDEX CODE_BASE_IX01
    ON DATACORE_UI.CODE_BASE USING btree
    (CODE_GROUP_ID);


-- 메뉴 기본 테이블 스키마
CREATE TABLE DATACORE_UI.MENU_BASE
(
    ID                  VARCHAR(20) NOT NULL,
    NAME                VARCHAR(50) NOT NULL,
    URL                 VARCHAR(100),
    UP_MENU_ID          VARCHAR(20),
    SORT_ORDER          NUMERIC(5),
    ENABLED             BOOLEAN NOT NULL DEFAULT TRUE,
    LEVEL               NUMERIC(5) NOT NULL,
    LANG_CD				VARCHAR(5) NOT NULL DEFAULT 'en',
    CREATE_DATETIME     TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CREATOR_ID          VARCHAR(64),
    MODIFY_DATETIME     TIMESTAMP WITHOUT TIME ZONE,
    MODIFIER_ID         VARCHAR(64),
    CONSTRAINT MENU_BASE_PK PRIMARY KEY (ID, LANG_CD)
);

-- 메뉴 역할 기본 테이블 스키마
CREATE TABLE DATACORE_UI.MENU_ROLE_BASE
(
    ID                  VARCHAR(20) NOT NULL,
    NAME                VARCHAR(50) NOT NULL,
    DESCRIPTION         VARCHAR(500),
    ENABLED             BOOLEAN NOT NULL DEFAULT TRUE,
    CREATE_DATETIME     TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CREATOR_ID          VARCHAR(64),
    MODIFY_DATETIME     TIMESTAMP WITHOUT TIME ZONE,
    MODIFIER_ID         VARCHAR(64),
    CONSTRAINT MENU_ROLE_BASE_PK PRIMARY KEY (ID)
);

-- 메뉴 역할 관계 기본 테이블 스키마
CREATE TABLE DATACORE_UI.MENU_ROLE_RELATION_BASE
(
    MENU_ID             VARCHAR(20) NOT NULL,
    MENU_ROLE_ID        VARCHAR(30) NOT NULL,
    CREATE_DATETIME     TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CREATOR_ID          VARCHAR(64),
    MODIFY_DATETIME     TIMESTAMP WITHOUT TIME ZONE,
    MODIFIER_ID         VARCHAR(64),
    CONSTRAINT MENU_ROLE_RELATION_BASE_PK PRIMARY KEY (MENU_ID, MENU_ROLE_ID),
    CONSTRAINT MENU_ROLE_RELATION_BASE_FK01 FOREIGN KEY (MENU_ROLE_ID)
    REFERENCES DATACORE_UI.MENU_ROLE_BASE (ID) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
);