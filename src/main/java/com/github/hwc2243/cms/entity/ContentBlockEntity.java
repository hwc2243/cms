package com.github.hwc2243.cms.entity;


import com.github.hwc2243.cms.entity.base.BaseContentBlockEntity;
import com.github.hwc2243.cms.entity.ContentPageEntity;
import com.github.hwc2243.cms.model.ContentBlock;
import com.github.hwc2243.cms.model.ContentBlockBlockType;
import com.github.hwc2243.cms.model.ContentBlockContentState;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.io.Serializable;

@Entity(name="ContentBlock")
@Table(name="contentBlock")
public class ContentBlockEntity
    extends BaseContentBlockEntity
    implements ContentBlock<ContentPageEntity>, Serializable
{
	public ContentBlockEntity ()
	{
		super();
	}
	
    // Private constructor to force the use of the Builder
    private ContentBlockEntity (Builder builder)
    {
        this.sortOrder = builder.sortOrder;
        this.blockType = builder.blockType;
        this.heading = builder.heading;
        this.body = builder.body;
        this.primaryDocumentId = builder.primaryDocumentId;
        this.secondaryDocumentIds = builder.secondaryDocumentIds;
        this.metadataJson = builder.metadataJson;
        this.visible = builder.visible;
        this.contentState = builder.contentState;
    }

    public static class Builder {

        private Integer sortOrder = null;
        private ContentBlockBlockType blockType = null;
        private String heading = null;
        private String body = null;
        private Long primaryDocumentId = null;
        private String secondaryDocumentIds = null;
        private String metadataJson = null;
        private Boolean visible = null;
        private ContentBlockContentState contentState = null;

        public Builder sortOrder(Integer sortOrder) {
            this.sortOrder = sortOrder;
            return this;
        }

        public Builder blockType(ContentBlockBlockType blockType) {
            this.blockType = blockType;
            return this;
        }

        public Builder heading(String heading) {
            this.heading = heading;
            return this;
        }

        public Builder body(String body) {
            this.body = body;
            return this;
        }

        public Builder primaryDocumentId(Long primaryDocumentId) {
            this.primaryDocumentId = primaryDocumentId;
            return this;
        }

        public Builder secondaryDocumentIds(String secondaryDocumentIds) {
            this.secondaryDocumentIds = secondaryDocumentIds;
            return this;
        }

        public Builder metadataJson(String metadataJson) {
            this.metadataJson = metadataJson;
            return this;
        }

        public Builder visible(Boolean visible) {
            this.visible = visible;
            return this;
        }

        public Builder contentState(ContentBlockContentState contentState) {
            this.contentState = contentState;
            return this;
        }

        /**
         * The build method creates and returns the immutable Entity object.
         */
        public ContentBlockEntity build() {
            return new ContentBlockEntity(this);
        }
    }
}
