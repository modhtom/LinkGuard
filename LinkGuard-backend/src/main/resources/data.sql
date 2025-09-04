CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE  IF NOT EXISTS users(
                       id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                       username TEXT NOT NULL UNIQUE,
                       email TEXT NOT NULL UNIQUE,
                       password_hash TEXT NOT NULL,
                       roles TEXT[] NOT NULL DEFAULT ARRAY['ROLE_USER']::TEXT[],
                       enabled BOOLEAN NOT NULL DEFAULT TRUE,
                       created_at timestamptz NOT NULL DEFAULT now(),
                       updated_at timestamptz NOT NULL DEFAULT now()
);

CREATE INDEX idx_users_email ON users(email);

CREATE TABLE  IF NOT EXISTS UrlExpansion(
                        id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                        shortUrl TEXT NOT NULL UNIQUE,
                        finalUrl TEXT NOT NULL,
                        domain TEXT NOT NULL,
                        title TEXT NOT NULL,
                        description TEXT NOT NULL,
                        created_at timestamptz NOT NULL DEFAULT now(),
                        resolved_by_id UUID NOT NULL,
                        CONSTRAINT fk_users FOREIGN KEY(resolved_by_id) REFERENCES users(id)
);

CREATE INDEX UrlExpansion_shortUrl ON UrlExpansion(shortUrl);