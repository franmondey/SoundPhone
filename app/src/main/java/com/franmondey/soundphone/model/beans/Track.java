/**
 * File generated by Magnet rest2mobile 1.1 - May 20, 2015 11:24:35 AM
 * @see {@link http://developer.magnet.com}
 */

package com.franmondey.soundphone.model.beans;


/**
 * Generated from json example
{
  "artwork_url" : "https://i1.sndcdn.com/artworks-000117434109-3qbumy-large.jpg",
  "comment_count" : 0,
  "commentable" : true,
  "description" : "Info+Tracklist:http://runthetrap.com/2015/05/20/too-future-guest-mix-032-ekali/\n\nEkali\nhttps://soundcloud.com/ekalimusic\nhttps://twitter.com/EkaliMusic\nhttps://www.facebook.com/ekalibeats\n\nTooFuture.\n@toofutureshop\nwww.facebook.com/toofuturexx\nwww.youtube.com/user/toofutureshop\ntwitter.com/toofuture\nwww.instagram.com/toofutureshop\nwww.toofuture.com(beta)",
  "download_count" : 0,
  "downloadable" : false,
  "embeddable_by" : "all",
  "favoritings_count" : 1,
  "genre" : "Ekali",
  "license" : "all-rights-reserved",
  "original_content_size" : 65785179,
  "original_format" : "mp3",
  "playback_count" : 8,
  "reposts_count" : 0,
  "state" : "finished",
  "streamable" : true,
  "tag_list" : "\"TooFuture\"\"Future\"\"FutureBass\"Trap",
  "user" : {
    "avatar_url" : "https://i1.sndcdn.com/avatars-000060418235-p5ut64-large.jpg",
      ...

 */

public class Track {

  
  private String artwork_url;

  
  private Integer comment_count;

  
  private Boolean commentable;

  
  private String created_at;

  
  private String description;

  
  private Integer download_count;

  
  private Boolean downloadable;

  
  private Integer duration;

  
  private String embeddable_by;

  
  private Integer favoritings_count;

  
  private String genre;

  
  private Integer id;

  
  private String isrc;

  
  private String kind;

  
  private String label_name;

  
  private String last_modified;

  
  private String license;

  
  private Integer likes_count;

  
  private Integer original_content_size;

  
  private String original_format;

  
  private String permalink;

  
  private String permalink_url;

  
  private Integer playback_count;

  
  private String policy;

  
  private Integer release_day;

  
  private Integer release_month;

  
  private Integer release_year;

  
  private Integer reposts_count;

  
  private String sharing;

  
  private String state;

  
  private String stream_url;

  
  private Boolean streamable;

  
  private String tag_list;

  
  private String title;

  
  private String track_type;

  
  private String uri;

  
  private User user;

  
  private Integer user_id;

  
  private String user_uri;

  
  private String waveform_url;

  public String getArtwork_url() {
    return artwork_url;
  }
  public Integer getComment_count() {
    return comment_count;
  }
  public Boolean getCommentable() {
    return commentable;
  }
  public String getCreated_at() {
    return created_at;
  }
  public String getDescription() {
    return description;
  }
  public Integer getDownload_count() {
    return download_count;
  }
  public Boolean getDownloadable() {
    return downloadable;
  }
  public Integer getDuration() {
    return duration;
  }
  public String getEmbeddable_by() {
    return embeddable_by;
  }
  public Integer getFavoritings_count() {
    return favoritings_count;
  }
  public String getGenre() {
    return genre;
  }
  public Integer getId() {
    return id;
  }
  public String getIsrc() {
    return isrc;
  }
  public String getKind() {
    return kind;
  }
  public String getLabel_name() {
    return label_name;
  }
  public String getLast_modified() {
    return last_modified;
  }
  public String getLicense() {
    return license;
  }
  public Integer getLikes_count() {
    return likes_count;
  }
  public Integer getOriginal_content_size() {
    return original_content_size;
  }
  public String getOriginal_format() {
    return original_format;
  }
  public String getPermalink() {
    return permalink;
  }
  public String getPermalink_url() {
    return permalink_url;
  }
  public Integer getPlayback_count() {
    return playback_count;
  }
  public String getPolicy() {
    return policy;
  }
  public Integer getRelease_day() {
    return release_day;
  }
  public Integer getRelease_month() {
    return release_month;
  }
  public Integer getRelease_year() {
    return release_year;
  }
  public Integer getReposts_count() {
    return reposts_count;
  }
  public String getSharing() {
    return sharing;
  }
  public String getState() {
    return state;
  }
  public String getStream_url() {
    return stream_url;
  }
  public Boolean getStreamable() {
    return streamable;
  }
  public String getTag_list() {
    return tag_list;
  }
  public String getTitle() {
    return title;
  }
  public String getTrack_type() {
    return track_type;
  }
  public String getUri() {
    return uri;
  }
  public User getUser() {
    return user;
  }
  public Integer getUser_id() {
    return user_id;
  }
  public String getUser_uri() {
    return user_uri;
  }
  public String getWaveform_url() {
    return waveform_url;
  }

  /**
  * Builder for Track
  **/
  public static class TrackBuilder {
    private Track toBuild = new Track();

    public TrackBuilder() {
    }

    public Track build() {
      return toBuild;
    }

    public TrackBuilder artwork_url(String value) {
      toBuild.artwork_url = value;
      return this;
    }
    public TrackBuilder comment_count(Integer value) {
      toBuild.comment_count = value;
      return this;
    }
    public TrackBuilder commentable(Boolean value) {
      toBuild.commentable = value;
      return this;
    }
    public TrackBuilder created_at(String value) {
      toBuild.created_at = value;
      return this;
    }
    public TrackBuilder description(String value) {
      toBuild.description = value;
      return this;
    }
    public TrackBuilder download_count(Integer value) {
      toBuild.download_count = value;
      return this;
    }
    public TrackBuilder downloadable(Boolean value) {
      toBuild.downloadable = value;
      return this;
    }
    public TrackBuilder duration(Integer value) {
      toBuild.duration = value;
      return this;
    }
    public TrackBuilder embeddable_by(String value) {
      toBuild.embeddable_by = value;
      return this;
    }
    public TrackBuilder favoritings_count(Integer value) {
      toBuild.favoritings_count = value;
      return this;
    }
    public TrackBuilder genre(String value) {
      toBuild.genre = value;
      return this;
    }
    public TrackBuilder id(Integer value) {
      toBuild.id = value;
      return this;
    }
    public TrackBuilder isrc(String value) {
      toBuild.isrc = value;
      return this;
    }
    public TrackBuilder kind(String value) {
      toBuild.kind = value;
      return this;
    }
    public TrackBuilder label_name(String value) {
      toBuild.label_name = value;
      return this;
    }
    public TrackBuilder last_modified(String value) {
      toBuild.last_modified = value;
      return this;
    }
    public TrackBuilder license(String value) {
      toBuild.license = value;
      return this;
    }
    public TrackBuilder likes_count(Integer value) {
      toBuild.likes_count = value;
      return this;
    }
    public TrackBuilder original_content_size(Integer value) {
      toBuild.original_content_size = value;
      return this;
    }
    public TrackBuilder original_format(String value) {
      toBuild.original_format = value;
      return this;
    }
    public TrackBuilder permalink(String value) {
      toBuild.permalink = value;
      return this;
    }
    public TrackBuilder permalink_url(String value) {
      toBuild.permalink_url = value;
      return this;
    }
    public TrackBuilder playback_count(Integer value) {
      toBuild.playback_count = value;
      return this;
    }
    public TrackBuilder policy(String value) {
      toBuild.policy = value;
      return this;
    }
    public TrackBuilder release_day(Integer value) {
      toBuild.release_day = value;
      return this;
    }
    public TrackBuilder release_month(Integer value) {
      toBuild.release_month = value;
      return this;
    }
    public TrackBuilder release_year(Integer value) {
      toBuild.release_year = value;
      return this;
    }
    public TrackBuilder reposts_count(Integer value) {
      toBuild.reposts_count = value;
      return this;
    }
    public TrackBuilder sharing(String value) {
      toBuild.sharing = value;
      return this;
    }
    public TrackBuilder state(String value) {
      toBuild.state = value;
      return this;
    }
    public TrackBuilder stream_url(String value) {
      toBuild.stream_url = value;
      return this;
    }
    public TrackBuilder streamable(Boolean value) {
      toBuild.streamable = value;
      return this;
    }
    public TrackBuilder tag_list(String value) {
      toBuild.tag_list = value;
      return this;
    }
    public TrackBuilder title(String value) {
      toBuild.title = value;
      return this;
    }
    public TrackBuilder track_type(String value) {
      toBuild.track_type = value;
      return this;
    }
    public TrackBuilder uri(String value) {
      toBuild.uri = value;
      return this;
    }
    public TrackBuilder user(User value) {
      toBuild.user = value;
      return this;
    }
    public TrackBuilder user_id(Integer value) {
      toBuild.user_id = value;
      return this;
    }
    public TrackBuilder user_uri(String value) {
      toBuild.user_uri = value;
      return this;
    }
    public TrackBuilder waveform_url(String value) {
      toBuild.waveform_url = value;
      return this;
    }
  }
}