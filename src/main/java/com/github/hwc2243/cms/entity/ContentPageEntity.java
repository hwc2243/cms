package com.github.hwc2243.cms.entity;


import com.github.hwc2243.cms.entity.base.BaseContentPageEntity;
import com.github.hwc2243.cms.model.ContentPage;
import com.github.hwc2243.cms.model.ContentPageStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity(name="ContentPage")
@Table(name="contentPage",
       uniqueConstraints = {
               @UniqueConstraint(columnNames = {
                       "slug"
               })
       }
)
public class ContentPageEntity
    extends BaseContentPageEntity
    implements ContentPage, Serializable
{
	public ContentPageEntity ()
	{
		super();
	}
	
    // Private constructor to force the use of the Builder
    private ContentPageEntity (Builder builder)
    {
        this.slug = builder.slug;
        this.title = builder.title;
        this.summary = builder.summary;
        this.status = builder.status;
        this.version = builder.version;
        this.publishedAt = builder.publishedAt;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
        this.createdBy = builder.createdBy;
        this.updatedBy = builder.updatedBy;
    }

    public static class Builder {

        private String slug = null;
        private String title = null;
        private String summary = null;
        private ContentPageStatus status = null;
        private Integer version = null;
        private LocalDateTime publishedAt = null;
        private LocalDateTime createdAt = null;
        private LocalDateTime updatedAt = null;
        private String createdBy = null;
        private String updatedBy = null;

        public Builder slug(String slug) {
            this.slug = slug;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder summary(String summary) {
            this.summary = summary;
            return this;
        }

        public Builder status(ContentPageStatus status) {
            this.status = status;
            return this;
        }

        public Builder version(Integer version) {
            this.version = version;
            return this;
        }

        public Builder publishedAt(LocalDateTime publishedAt) {
            this.publishedAt = publishedAt;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Builder createdBy(String createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public Builder updatedBy(String updatedBy) {
            this.updatedBy = updatedBy;
            return this;
        }

        /**
         * The build method creates and returns the immutable Entity object.
         */
        public ContentPageEntity build() {
            return new ContentPageEntity(this);
        }
    }
}
