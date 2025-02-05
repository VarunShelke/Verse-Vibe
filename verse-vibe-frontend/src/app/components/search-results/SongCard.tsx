import { Song } from '@/app/models/models';
import { ExplicitOutlined, OpenInNew as OpenInNewIcon } from '@mui/icons-material';
import { Button, Card, CardContent, CardMedia, Typography } from '@mui/material';
import styles from './SongCard.module.css';
import noImagePlaceholder from '../../../assets/no-image-placeholder.svg';

export default function SongCard({ song }: { song: Song }) {
  let imageUrl = noImagePlaceholder.src;
  const albumImage = song?.album?.images?.find((image) => image.width === 300 && image.height === 300);
  if (albumImage) {
    imageUrl = albumImage.url;
  }

  return (
    <Card className={styles.card}>
      <CardMedia className={styles.image} component="img" image={imageUrl} alt={song.name} />

      <CardContent className={styles.content}>
        <Typography variant="h6" className={styles.title}>
          {song.explicit && <ExplicitOutlined />}
          {song.explicit && ' '}
          {song.name}
        </Typography>

        <Typography variant="body2" className={styles.text}>
          <strong>Artists: </strong>
          {song.artists.map((artist, index) => {
            if (artist.spotifyUrl) {
              return (
                <a
                  key={artist.spotifyId}
                  href={artist.spotifyUrl}
                  target="_blank"
                  rel="noopener noreferrer"
                  className={styles.artistLink}
                >
                  {artist.name}
                  {index < song.artists.length - 1 ? ', ' : ''}
                </a>
              );
            }

            return (
              <span key={`${song.elasticSearchId}-artist-${index}`}>
                {artist.name}
                {index < song.artists.length - 1 ? ', ' : ''}
              </span>
            );
          })}
        </Typography>

        <Typography variant="body2" className={styles.text}>
          <strong>Album:</strong> {song.album.name}
        </Typography>

        <Typography variant="body2" className={styles.text}>
          <strong>Release Year:</strong> {song.album.releaseDate}
        </Typography>

        {song.duration && (
          <Typography variant="body2" className={styles.text}>
            <strong>Duration:</strong> {song.duration}
          </Typography>
        )}
      </CardContent>

      <Button
        className={styles.spotifyButton}
        href={song.spotifyUrl || `#${song.name.toLowerCase().split(' ').join('-')}`}
        target="_blank"
        rel="noopener noreferrer"
      >
        Open in Spotify
        <OpenInNewIcon />
      </Button>
    </Card>
  );
}
