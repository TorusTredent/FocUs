ALTER TABLE users_custom_pack
    ADD CONSTRAINT uc_users_custom_pack_custompack UNIQUE (custom_pack_id);

ALTER TABLE users
    ADD CONSTRAINT FK_USERS_ON_PHOTO_PATH FOREIGN KEY (photo_path_id) REFERENCES photos (id);

ALTER TABLE users
    ADD CONSTRAINT FK_USERS_ON_USEDPACK FOREIGN KEY (used_pack_id) REFERENCES packs (id);

ALTER TABLE tasks_user_task_status_mapping
    ADD CONSTRAINT fk_tasks_user_task_status_mapping_on_task FOREIGN KEY (task_id) REFERENCES tasks (id);

ALTER TABLE tasks_user_task_status_mapping
    ADD CONSTRAINT fk_tasks_user_task_status_mapping_on_user FOREIGN KEY (users_id) REFERENCES users (id);

ALTER TABLE users_category
    ADD CONSTRAINT fk_usecat_on_category FOREIGN KEY (category_id) REFERENCES categories (id);

ALTER TABLE users_category
    ADD CONSTRAINT fk_usecat_on_user FOREIGN KEY (users_id) REFERENCES users (id);

ALTER TABLE users_custom_pack
    ADD CONSTRAINT fk_usecuspac_on_pack FOREIGN KEY (custom_pack_id) REFERENCES packs (id);

ALTER TABLE users_custom_pack
    ADD CONSTRAINT fk_usecuspac_on_user FOREIGN KEY (users_id) REFERENCES users (id);

ALTER TABLE users_followers
    ADD CONSTRAINT fk_usefol_on_followers FOREIGN KEY (followers_id) REFERENCES users (id);

ALTER TABLE users_followers
    ADD CONSTRAINT fk_usefol_on_users FOREIGN KEY (users_id) REFERENCES users (id);

ALTER TABLE users_subscriptions
    ADD CONSTRAINT fk_usesub_on_subscriptions FOREIGN KEY (subscriptions_id) REFERENCES users (id);

ALTER TABLE users_subscriptions
    ADD CONSTRAINT fk_usesub_on_users FOREIGN KEY (users_id) REFERENCES users (id);

ALTER TABLE users_tasks
    ADD CONSTRAINT fk_usetas_on_task FOREIGN KEY (tasks_id) REFERENCES tasks (id);

ALTER TABLE users_tasks
    ADD CONSTRAINT fk_usetas_on_user FOREIGN KEY (users_id) REFERENCES users (id);

ALTER TABLE tasks
    ADD CONSTRAINT FK_TASKS_ON_AUTHOR FOREIGN KEY (author_id) REFERENCES users (id);

ALTER TABLE tasks_category
    ADD CONSTRAINT fk_tascat_on_category FOREIGN KEY (category_id) REFERENCES categories (id);

ALTER TABLE tasks_category
    ADD CONSTRAINT fk_tascat_on_task FOREIGN KEY (tasks_id) REFERENCES tasks (id);

ALTER TABLE tasks_friends
    ADD CONSTRAINT fk_tasfri_on_task FOREIGN KEY (tasks_id) REFERENCES tasks (id);

ALTER TABLE tasks_friends
    ADD CONSTRAINT fk_tasfri_on_user FOREIGN KEY (friends_id) REFERENCES users (id);

ALTER TABLE packs
    ADD CONSTRAINT FK_PACKS_ON_AUTHOR FOREIGN KEY (author_id) REFERENCES users (id);

ALTER TABLE packs_followers
    ADD CONSTRAINT fk_pacfol_on_pack FOREIGN KEY (packs_id) REFERENCES packs (id);

ALTER TABLE packs_followers
    ADD CONSTRAINT fk_pacfol_on_user FOREIGN KEY (followers_id) REFERENCES users (id);

ALTER TABLE packs_ranks
    ADD CONSTRAINT fk_pacran_on_pack FOREIGN KEY (packs_id) REFERENCES packs (id);

ALTER TABLE packs_ranks
    ADD CONSTRAINT fk_pacran_on_rank FOREIGN KEY (ranks_id) REFERENCES ranks (id);