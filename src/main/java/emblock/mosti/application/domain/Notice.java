package emblock.mosti.application.domain;

import java.time.LocalDateTime;

public class Notice {
    private long id;
    private String writer;
    private String title;
    private String content;
    LocalDateTime createdOn;

    private Notice(String writer, String title, String content) {
        this.writer = writer;
        this.title = title;
        this.content = content;
        createdOn = LocalDateTime.now();
    }

    private Notice(long id, String writer, String title, String content, LocalDateTime createdOn) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.createdOn = createdOn;
    }

    public String getTitle() {
        return title;
    }

    public long getId() {
        return id;
    }

    public String getWriter() {
        return writer;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }


    public static class Builder {
        private long id;
        private String writer;
        private String title;
        private String content;
        LocalDateTime createdOn;

        public static Builder builder() {
            return new Builder();
        }

        public Builder() {
            id = -1;
            writer = null;
            title = null;
            content = null;
            createdOn = LocalDateTime.now();
        }
        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setWriter(String writer) {
            this.writer = writer;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public Builder setCreatedOn(LocalDateTime createdOn) {
            this.createdOn = createdOn;
            return this;
        }

        public Notice build() {
            return new Notice(id, writer, title, content, createdOn);
        }
    }

}
