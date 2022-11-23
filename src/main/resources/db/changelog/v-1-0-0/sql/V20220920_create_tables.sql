CREATE TABLE if not exists users
(
    id               BIGINT AUTO_INCREMENT NOT NULL,
    firebase_id      VARCHAR(255)          NULL,
    username         VARCHAR(255)          NULL,
    email            VARCHAR(255)          NULL,
    photo_path_id    BIGINT                NULL,
    used_pack_id     BIGINT                NULL,
    user_role        VARCHAR(255) default 'USER',
    user_task_status VARCHAR(255)          NULL,
    user_status      VARCHAR(255) default 'ACTIVE',
    CONSTRAINT pk_users PRIMARY KEY (id)
);

CREATE TABLE if not exists tasks
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    name          VARCHAR(255)          NULL,
    create_date   datetime              NOT NULL,
    dead_line     datetime              NOT NULL,
    author_id     BIGINT                NOT NULL,
    task_priority VARCHAR(255)          NULL,
    task_status   VARCHAR(255)          NULL,
    task_privacy  VARCHAR(255)          NULL,
    task_type     VARCHAR(255)          NULL,
    CONSTRAINT pk_tasks PRIMARY KEY (id)
);

CREATE TABLE if not exists categories
(
    id                    BIGINT AUTO_INCREMENT NOT NULL,
    name                  VARCHAR(255)          NULL,
    text_color            VARCHAR(255)          NULL,
    text_background_color VARCHAR(255)          NULL,
    category_type         VARCHAR(255)          NULL,
    CONSTRAINT pk_categories PRIMARY KEY (id)
);

CREATE TABLE if not exists packs
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    author_id     BIGINT                NULL,
    `description` VARCHAR(255)          NULL,
    pack_type     VARCHAR(255)          NULL,
    CONSTRAINT pk_packs PRIMARY KEY (id)
);

CREATE TABLE if not exists ranks
(
    id                  BIGINT AUTO_INCREMENT NOT NULL,
    name                VARCHAR(255)          NULL,
    max_completed_tasks BIGINT                NULL,
    min_completed_tasks BIGINT                NULL,
    CONSTRAINT pk_ranks PRIMARY KEY (id)
);

CREATE TABLE if not exists photos
(
    id            BIGINT       NOT NULL,
    photo_title   VARCHAR(255) NULL,
    upload_date   datetime     NOT NULL,
    extension     VARCHAR(255) NULL,
    download_link VARCHAR(255) NULL,
    CONSTRAINT pk_photos PRIMARY KEY (id)
);

CREATE TABLE if not exists tasks_user_task_status_mapping
(
    users_id             BIGINT       NOT NULL,
    user_task_status_map VARCHAR(255),
    task_id              BIGINT       NOT NULL,
    CONSTRAINT pk_tasks_user_task_status_mapping PRIMARY KEY (users_id, task_id)
);

CREATE TABLE if not exists users_category
(
    category_id BIGINT NOT NULL,
    users_id    BIGINT NOT NULL
);

CREATE TABLE if not exists users_custom_pack
(
    custom_pack_id BIGINT NOT NULL,
    users_id       BIGINT NOT NULL
);

CREATE TABLE if not exists users_followers
(
    followers_id BIGINT NOT NULL,
    users_id     BIGINT NOT NULL
);

CREATE TABLE if not exists users_subscriptions
(
    subscriptions_id BIGINT NOT NULL,
    users_id         BIGINT NOT NULL
);

CREATE TABLE if not exists users_tasks
(
    tasks_id BIGINT NOT NULL,
    users_id BIGINT NOT NULL
);


CREATE TABLE if not exists tasks_category
(
    category_id BIGINT NOT NULL,
    tasks_id    BIGINT NOT NULL
);

CREATE TABLE if not exists tasks_friends
(
    friends_id BIGINT NOT NULL,
    tasks_id   BIGINT NOT NULL
);

CREATE TABLE if not exists packs_followers
(
    followers_id BIGINT NOT NULL,
    packs_id     BIGINT NOT NULL
);

CREATE TABLE if not exists packs_ranks
(
    packs_id BIGINT NOT NULL,
    ranks_id BIGINT NOT NULL
);


