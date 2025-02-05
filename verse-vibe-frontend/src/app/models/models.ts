interface SongMetadata {
    spotifyId?: string;
    name: string;
    spotifyUrl?: string;
}

// eslint-disable-next-line @typescript-eslint/no-empty-object-type
interface Artist extends SongMetadata {}

interface Image {
    url: string;
    width: number;
    height: number;
}

interface Album extends SongMetadata {
    releaseDate?: string;
    images?: Image[];
}

export interface Song extends SongMetadata {
    docId: number;
    elasticSearchId: string;
    artists: Artist[];
    album: Album;
    duration?: string;
    explicit?: boolean;
}

