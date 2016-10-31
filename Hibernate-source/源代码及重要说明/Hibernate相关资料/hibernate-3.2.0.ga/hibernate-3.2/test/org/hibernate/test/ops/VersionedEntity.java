package org.hibernate.test.ops;

/**
 * todo: describe VersionedEntity
 *
 * @author Steve Ebersole
 */
public class VersionedEntity {
	private String id;
	private String name;
	private long version;

	public VersionedEntity() {
	}

	public VersionedEntity(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}
}
